import pandas as pd
import random
from nltk.corpus import wordnet
import nltk
from sklearn.preprocessing import StandardScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, LSTM, Embedding, Bidirectional, Dropout
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.losses import MeanSquaredError
from tensorflow.keras.callbacks import EarlyStopping, LearningRateScheduler
import numpy as np


nltk.download('wordnet')

# Đọc dữ liệu từ file CSV hoặc DataFrame
data = pd.read_csv('data.csv')

# Tiền xử lý dữ liệu
X = data['Task'].values
y = data['Difficulty'].values

# Hàm tăng cường dữ liệu bằng cách thay đổi từ đồng nghĩa và đảo ngữ câu
def augment_text(text):
    words = text.split()
    
    # Thay đổi từ đồng nghĩa
    for i in range(len(words)):
        # Tạo danh sách từ đồng nghĩa của từ hiện tại (nếu có)
        synonyms = get_synonyms(words[i])
        if synonyms:
            # Chọn một từ đồng nghĩa ngẫu nhiên và thay thế từ hiện tại
            words[i] = random.choice(synonyms)
    
    # Đảo ngữ câu
    random.shuffle(words)
    
    # Gắn các từ lại thành một câu mới
    return ' '.join(words)

# Hàm lấy danh sách từ đồng nghĩa của một từ từ một nguồn dữ liệu nào đó (ví dụ: từ điển)
def get_synonyms(word):
    synonyms = []

    # Lặp qua các từ đồng nghĩa của từ hiện tại từ WordNet
    for syn in wordnet.synsets(word):
        for lemma in syn.lemmas():
            synonyms.append(lemma.name())

    # Loại bỏ các từ trùng lặp và trả về danh sách từ đồng nghĩa
    return list(set(synonyms))

# Áp dụng tăng cường dữ liệu cho tập dữ liệu hiện có
def data_augmentation(X, y, augmentation_factor=2):
    augmented_X = []
    augmented_y = []
    for i in range(len(X)):
        augmented_X.append(X[i])
        augmented_y.append(y[i])
        for _ in range(augmentation_factor):
            augmented_X.append(augment_text(X[i]))
            augmented_y.append(y[i])
    return augmented_X, augmented_y

# Sử dụng hàm data_augmentation để tăng cường dữ liệu cho tập dữ liệu hiện có
X_augmented, y_augmented = data_augmentation(X, y)

# Kết hợp dữ liệu gốc và dữ liệu tăng cường
X_combined = list(X) + X_augmented
y_combined = np.array(y_augmented).reshape(-1, 1)

# Tạo tập dữ liệu đầu vào và đầu ra
max_words = 2000  # Tăng số lượng từ tối đa trong từ điển
max_len = 1000
embedding_dim = 50

tokenizer = Tokenizer(num_words=max_words)
tokenizer.fit_on_texts(X_combined)
X_seq = tokenizer.texts_to_sequences(X_combined)

X_pad = pad_sequences(X_seq, maxlen=max_len)

scaler = StandardScaler()
X_pad = X_pad[:len(y_combined)]

print("Shape of X_pad:", X_pad.shape)
print("Shape of y_combined:", len(y_combined))


# Xác định kiến trúc mô hình RNN với Dropout
model = Sequential()
model.add(Embedding(input_dim=max_words, output_dim=embedding_dim, input_length=max_len))
model.add(Bidirectional(LSTM(128, return_sequences=True)))
model.add(Dropout(0.2))  # Thêm dropout
model.add(Bidirectional(LSTM(64, return_sequences=True)))  # Thêm một lớp LSTM
model.add(Dropout(0.2))  # Thêm dropout
model.add(Bidirectional(LSTM(32)))
model.add(Dense(64, activation='relu'))
model.add(Dense(1, activation='linear'))

# Biên soạn mô hình
lr_schedule = LearningRateScheduler(lambda epoch: 1e-3 * 10**(epoch / 20))  # Sử dụng learning rate decay
model.compile(optimizer=Adam(), loss=MeanSquaredError(), metrics=['accuracy'])

# Huấn luyện mô hình
early_stopping = EarlyStopping(monitor='val_loss', patience=3, restore_best_weights=True)
history = model.fit(X_pad, y_combined, epochs=20, batch_size=32, validation_split=0.2, callbacks=[early_stopping, lr_schedule])

# Đánh giá mô hình
mse, acc = model.evaluate(X_pad, y_combined)
print('Mean Squared Error:', mse)
print('Accuracy:', acc)

# Dự đoán cho một task cụ thể
specific_task = "Xây dựng giao diện người dùng cho chức năng đăng nhập"
specific_task_seq = tokenizer.texts_to_sequences([specific_task])
specific_task_pad = pad_sequences(specific_task_seq, maxlen=max_len)
specific_task_pad = scaler.transform(specific_task_pad)
predicted_difficulty = model.predict(specific_task_pad)

print("Task:", specific_task)
print("Predicted Difficulty:", predicted_difficulty[0][0])
