@Override
    protected void parseNextStatement(Marker marker) {
        if (tokens.matches(DdlTokenizer.COMMENT)) {
            parseComment(marker);
        } else if (tokens.matches("CREATE")) {
            parseCreate(marker);
        } else if (tokens.matches("ALTER")) {
            parseAlter(marker);
        } else if (tokens.matches("DROP")) {
            parseDrop(marker);
        } else if (tokens.matches("RENAME")) {
            parseRename(marker);
        } else {
            parseUnknownStatement(marker);
        }
    }