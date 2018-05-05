# 1.
sudo cp antlr-4.7.1-complete.jar /usr/local/lib/
# 2. and 3.
# add this to your .bash_profile
export CLASSPATH = ".:/usr/local/lib/antlr-4.7.1-complete.jar:$CLASSPATH"
# simplify the use of the tool to generate lexer and parser
antlr4='java -Xmx500M -cp "/usr/local/lib/antlr-4.7.1-complete.jar:$CLASSPATH" org.antlr.v4.Tool'
# simplify the use of the tool to test the generated code
alias grun='java org.antlr.v4.gui.TestRig'