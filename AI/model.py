import pandas as pd
from sklearn.preprocessing import StandardScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, LSTM, Embedding, Bidirectional, Dropout
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.losses import MeanSquaredError
from tensorflow.keras.callbacks import EarlyStopping, LearningRateScheduler

# Load data
data = pd.read_csv('data.csv')

# Preprocess data
X = data['Task'].values
y = data['Difficulty'].values

# Create input and output sequences
max_words = 2000
max_len = 1000
embedding_dim = 50

tokenizer = Tokenizer(num_words=max_words)
tokenizer.fit_on_texts(X)
X_seq = tokenizer.texts_to_sequences(X)
X_pad = pad_sequences(X_seq, maxlen=max_len)

scaler = StandardScaler()
X_pad = scaler.fit_transform(X_pad)

# Define RNN model with Dropout
model = Sequential()
model.add(Embedding(input_dim=max_words, output_dim=embedding_dim, input_length=max_len))
model.add(Bidirectional(LSTM(128, return_sequences=True)))
model.add(Dropout(0.2))
model.add(Bidirectional(LSTM(64, return_sequences=True)))
model.add(Dropout(0.2))
model.add(Bidirectional(LSTM(32)))
model.add(Dense(64, activation='relu'))
model.add(Dense(1, activation='linear'))

# Compile model
lr_schedule = LearningRateScheduler(lambda epoch: 1e-3 * 10**(epoch / 20))
model.compile(optimizer=Adam(), loss=MeanSquaredError(), metrics=['accuracy'])

# Train model
early_stopping = EarlyStopping(monitor='val_loss', patience=3, restore_best_weights=True)
history = model.fit(X_pad, y, epochs=20, batch_size=32, validation_split=0.2, callbacks=[early_stopping, lr_schedule])

# Evaluate model
mse, acc = model.evaluate(X_pad, y)
print('Mean Squared Error:', mse)
print('Accuracy:', acc)

# Save model
model.save('task_difficulty_predictor.h5')
