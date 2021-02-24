import java.util.Date;

public class KashMoneyIssue {
    // Enum
    public enum KashMoneyIssueStatus {
        OPEN, CLOSED
    }

    public enum KashMoneyIssueResponseType {
        LETTER, EMAIL, CALL
    }

    // Immutable attributes
    private final String issuer;
    private final String content;
    private final String transactionId;

    // Other attributes
    private KashMoneyIssueStatus status;
    private KashMoneyAccount responder;
    private KashMoneyIssueResponseType responseType;
    private Date responseDate;

    // Methods
    public String getIssuer() {
        return issuer;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return transactionId;
    }

    public KashMoneyIssueStatus getStatus() {
        return status;
    }

    public void setStatus(KashMoneyIssueStatus status) {
        this.status = status;
    }

    public KashMoneyAccount getResponder() {
        return responder;
    }

    public void setResponder(KashMoneyAccount responder) {
        this.responder = responder;
        responseDate = new Date();
    }

    public KashMoneyIssueResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(KashMoneyIssueResponseType responseType) {
        this.responseType = responseType;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    KashMoneyIssue(String issuer, String content, String transactionId) {
        this.issuer = issuer;
        this.content = content;
        this.transactionId = transactionId;


        status = KashMoneyIssueStatus.OPEN;
    }
}