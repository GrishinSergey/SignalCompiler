package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;

abstract class AbstractRule {

    void throwExceptionIfUnexpectedUnsignedInteger(int code) throws ParserException {
        if (500 > code) {
            throw new ParserException(ErrorMessages.UNEXPECTED_INTEGER);
        }
    }

    void throwExceptionIfUnexpectedEndOfLine(int code) throws ParserException {
        if (5 != code) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LINE);
        }
    }

    void throwExceptionIfUnexpectedIdentifier(int code) throws ParserException {
        if (200 > code || 500 < code) {
            throw new ParserException(ErrorMessages.UNEXPECTED_VARIABLE);
        }
    }

    void throwExceptionIfUnexpectedIdentifier(int code, String errorString) throws ParserException {
        if (200 > code || 500 < code) {
            throw new ParserException(errorString);
        }
    }

    boolean isIdentifier(int code) {
        return 200 < code && 500 > code;
    }

}
