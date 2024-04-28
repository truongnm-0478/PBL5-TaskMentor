package dto.request;
import java.util.Base64;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequirementRequest {
    private String teamId;
    private String title;
    private String contentBase64;

    public byte[] getContent() {
        return Base64.getDecoder().decode(contentBase64);
    }
}
