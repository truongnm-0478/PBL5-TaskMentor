import pandas as pd
from sklearn.preprocessing import StandardScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, LSTM, Embedding, Bidirectional, Dropout
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.losses import MeanSquaredError
from tensorflow.keras.callbacks import EarlyStopping, LearningRateScheduler

# Đọc dữ liệu từ file CSV hoặc DataFrame
data = pd.read_csv('data.csv')

# Tiền xử lý dữ liệu
X = data['Task'].values
y = data['Difficulty'].values

# Tạo tập dữ liệu đầu vào và đầu ra
max_words = 2000  # Tăng số lượng từ tối đa trong từ điển
max_len = 1000
embedding_dim = 50

tokenizer = Tokenizer(num_words=max_words)
tokenizer.fit_on_texts(X)
X_seq = tokenizer.texts_to_sequences(X)

X_pad = pad_sequences(X_seq, maxlen=max_len)

scaler = StandardScaler()
X_pad = scaler.fit_transform(X_pad)

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
history = model.fit(X_pad, y, epochs=20, batch_size=32, validation_split=0.2, callbacks=[early_stopping, lr_schedule])

# Đánh giá mô hình
mse, acc = model.evaluate(X_pad, y)
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
