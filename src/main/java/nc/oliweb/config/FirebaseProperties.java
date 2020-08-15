package nc.oliweb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Venteenligne Springboot.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "firebase", ignoreUnknownFields = false)
public class FirebaseProperties {
    private String cspFrameSrc = "";

    private String cspConnectSrc = "";

    private String cspImageSrc = "";

    private String cspScriptSrc = "";

    public String getCspFrameSrc() {
        return cspFrameSrc;
    }

    public void setCspFrameSrc(String cspFrameSrc) {
        this.cspFrameSrc = cspFrameSrc;
    }

    public String getCspConnectSrc() {
        return cspConnectSrc;
    }

    public void setCspConnectSrc(String cspConnectSrc) {
        this.cspConnectSrc = cspConnectSrc;
    }

    public String getCspImageSrc() {
        return cspImageSrc;
    }

    public void setCspImageSrc(String cspImageSrc) {
        this.cspImageSrc = cspImageSrc;
    }

    public String getCspScriptSrc() {
        return cspScriptSrc;
    }

    public void setCspScriptSrc(String cspScriptSrc) {
        this.cspScriptSrc = cspScriptSrc;
    }
}
