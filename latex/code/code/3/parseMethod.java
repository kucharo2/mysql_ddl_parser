public final void parse(String ddlContent, Tables databaseTables) {
        Tokenizer tokenizer = new DdlTokenizer(!skipComments(), this::determineTokenType);
        TokenStream stream = new TokenStream(ddlContent, tokenizer, false);
        stream.start();
        parse(stream, databaseTables);
    }
    
public final void parse(TokenStream ddlContent, Tables databaseTables) throws ParsingException, IllegalStateException {
        this.tokens = ddlContent;
        this.databaseTables = databaseTables;
        Marker marker = ddlContent.mark();
        try {
            while (ddlContent.hasNext()) {
                parseNextStatement(ddlContent.mark());
                // Consume the statement terminator if it is still there ...
                tokens.canConsume(DdlTokenizer.STATEMENT_TERMINATOR);
            }
        } catch (ParsingException e) {
            ddlContent.rewind(marker);
            throw e;
        } catch (Throwable t) {
            parsingFailed(ddlContent.nextPosition(), "Unexpected exception (" + t.getMessage() + ") parsing", t);
        }
    }