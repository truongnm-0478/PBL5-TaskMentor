from flask import Flask, request, jsonify
import numpy as np
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.preprocessing.text import Tokenizer
from sklearn.preprocessing import StandardScaler
import pandas as pd

app = Flask(__name__)

# Load the trained model
model = load_model('task_difficulty_predictor.h5')

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
scaler.fit(X_pad)  # Fit StandardScaler on your training data

# Define the maximum length of input sequence
max_len = 1000

# API route for predicting task difficulty
@app.route('/predict_task_difficulty', methods=['POST'])
def predict_task_difficulty():
    try:
        # Get input data
        task_name = request.json.get('task_name', '')

        # Tokenize input text
        X_seq = tokenizer.texts_to_sequences([task_name])
        X_pad = pad_sequences(X_seq, maxlen=max_len)

        # Scale the input data
        X_pad = scaler.transform(X_pad)

        # Make prediction
        predicted_difficulty = model.predict(X_pad)

        # Round the predicted difficulty to 2 decimal places
        predicted_difficulty = np.round(predicted_difficulty, 2)

        # Convert predicted difficulty to a type that is JSON serializable
        predicted_difficulty = float(predicted_difficulty[0][0])

        return jsonify({'predicted_difficulty': predicted_difficulty}), 200
    except Exception as e:
        print(e)
        return jsonify({'error': 'An error occurred while processing the request'}), 500


if __name__ == '__main__':
    app.run(debug=True)
