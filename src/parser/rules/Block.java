package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.List;

class Block {

    BlockToken getBlock(List<List<ParserToken>> declarations, ScannerList scannerTokenList) throws ParserException {
        /* @TODO: add declarations */
        if (102 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_START_OF_BLOCK);
        }
        return new BlockToken("block",
                scannerTokenList.getCurrentScannerToken().getLineNumber(), declarations,
                new StatementsList().getStatementsList(scannerTokenList.getRestOfScannerToken()));
    }

    private class BlockToken extends ParserToken {

        private List<List<ParserToken>> declarations;
        private List<ParserToken> statements;

        BlockToken(String token, int line, List<List<ParserToken>> declarations, List<ParserToken> statements) {
            super(token, line);
            this.declarations = declarations;
            this.statements = statements;
        }
    }

}
