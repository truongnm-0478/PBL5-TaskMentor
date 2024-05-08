package util;

public class FormatUtil {
    public double format(double predictedDifficulty, String lowercaseDescription) {
        double randomValue = Math.random();
        predictedDifficulty += randomValue - 0.3;

        // Check if taskDescription contains any of the specified keywords and adjust difficulty accordingly
        if (lowercaseDescription.contains("ai") || lowercaseDescription.contains("tranning") || lowercaseDescription.contains("học máy") || lowercaseDescription.contains("nhân tạo")) {
            predictedDifficulty += 6;
        } else if (lowercaseDescription.contains("ui") || lowercaseDescription.contains("giao diện") || lowercaseDescription.contains("màn hình")) {
            predictedDifficulty -= 2;
        } else if (lowercaseDescription.contains("database") || lowercaseDescription.contains("cơ sở dữ liệu")) {
            predictedDifficulty += 3;
        }

        return predictedDifficulty;
    }
}
