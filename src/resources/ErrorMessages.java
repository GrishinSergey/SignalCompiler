package resources;

abstract public class ErrorMessages {

    public static final String UNCLOSED_COMMENT = "Scanner error: unclosed comment ";

    public static final String UNEXPECTED_BEGIN_OF_PROGRAM = "Unexpected begin of program. Expected PROGRAM or PROCEDURE ";
    public static final String UNEXPECTED_END_OF_FILE = "Unexpected end of program ";
    public static final String UNEXPECTED_PROGRAM_NAME = "Unexpected program name. Expected Identifier ";
    public static final String UNEXPECTED_START_OF_BLOCK = "Unexpected start of block. Expected BEGIN ";
    public static final String UNEXPECTED_END_OF_LINE = "Unexpected end of line. Expected semicolon ";
    public static final String UNEXPECTED_TOKEN = "Unexpected token ";
    public static final String UNEXPECTED_VARIABLE = "Unexpected variable identifier ";
    public static final String UNEXPECTED_INTEGER = "Unexpected integer ";
    public static final String UNEXPECTED_DELIMITER = "Unexpected token. Expected coma ";
    public static final String UNEXPECTED_END_OF_LOOP = "Unexpected end of loop. Expected ENDLOOP ";
    public static final String UNEXPECTED_PATH_TO_ASSEMBLY_INSERT_FILE = "File with assembly insert doesn\'t exists. ";
    public static final String UNEXPECTED_END_OF_ASSEMBLY_INSERT = "Unexpected end of assembly insert. Expected $) ";
    public static final String UNEXPECTED_ASSEMBLY_INSERT_FILE = "Unexpected assembly insert file type. ";
    public static final String UNEXPECTED_PROCEDURE_CALL = "Unexpected procedure call ";
    public static final String UNEXPECTED_VARIABLES_LIST = "Unexpected list of variables ";
    public static final String UNEXPECTED_DECLARATION = "Unexpected declaration ";
    public static final String UNEXPECTED_SYMBOL_IN_VAR_DECLARATION = "Unexpected symbol in variable declaration. Expected : ";
    public static final String UNEXPECTED_ATTRIBUTE_IN_PROCEDURE_DECLARATION = "Unexpected attribute ";
    public static final String UNEXPECTED_END_OF_ATTRIBUTE_LIST = "Expected ) ";
    public static final String UNEXPECTED_EXPRESSION_OF_FUNCTION = "expected SIN ";
    public static final String UNEXPECTED_FUNCTIONS_ATTRIBUTE = "expected \\ ";
    public static final String UNEXPECTED_ASSUMING_OF_FUNCTION_BODY = "expected assume ";
    public static final String UNEXPECTED_LABEL_DECLARATION = "error in labels declaration ";

    public static final String DECLARATION_ERROR = " was already defined at line ";

}
