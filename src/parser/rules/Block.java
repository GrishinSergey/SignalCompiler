package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.List;

class Block {

    BlockToken getBlock(ScannerList scannerTokenList) throws ParserException {
        /* @TODO: add declarations */
        if (102 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_START_OF_BLOCK);
        }
        BlockToken blockToken = new BlockToken("block",
                scannerTokenList.getCurrentScannerToken().getLineNumber());
        blockToken.statements = new StatementsList().getStatementsList(scannerTokenList.getRestOfScannerToken());
        blockToken.declarations = null;
        return blockToken;
    }

    private class BlockToken extends ParserToken {

        private List<ParserToken> declarations;
        private List<ParserToken> statements;

        BlockToken(String token, int line) {
            super(token, line);
        }

        public List<ParserToken> getDeclarations() {
            return declarations;
        }

        public List<ParserToken> getStatements() {
            return statements;
        }

    }

}
