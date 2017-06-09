package generator.tokens;

import resources.token.GeneratorToken;

public class PortToken extends GeneratorToken {

    private String portName;
    private String portValue;
    private String portType;

    public PortToken(int lineNumber, String portName, String portValue) {
        super("port", lineNumber);
        this.portName = portName;
        this.portValue = portValue;
        this.portType = null;
    }

    @Override
    public String toString() {
        return portValue;
    }

    public String getPortName() {
        return portName;
    }

    public String getPortValue() {
        return portValue;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortInType() {
        this.portType = "in";
    }

    public void setPortOutType() {
        this.portType = "out";
    }

}
