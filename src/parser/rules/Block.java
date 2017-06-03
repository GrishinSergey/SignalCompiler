package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.List;

public class Block {

    BlockToken getBlock(
            DeclarationsList.DeclarationsListToken declarations,
            ScannerList scannerTokenList
    ) throws ParserException {
        if (102 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_START_OF_BLOCK);
        }
        return new BlockToken("block",
                scannerTokenList.getCurrentScannerToken().getLine(), declarations,
                new StatementsList().getStatementsList(scannerTokenList.getRestOfScannerToken()));
    }

    public class BlockToken extends ParserToken {

        private DeclarationsList.DeclarationsListToken declarations;
        private List<ParserToken> statements;

        BlockToken(
                String token,
                int line,
                DeclarationsList.DeclarationsListToken declarations,
                List<ParserToken> statements
        ) {
            super(token, line);
            this.declarations = declarations;
            this.statements = statements;
        }

        public DeclarationsList.DeclarationsListToken getDeclarations() {
            return declarations;
        }

        public List<ParserToken> getStatements() {
            return statements;
        }
    }

}
