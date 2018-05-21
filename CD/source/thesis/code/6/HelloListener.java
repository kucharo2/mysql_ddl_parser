public class HelloListenerCustom extends HelloBaseListener {
  $$@Override
  public void enterGreeting(HelloParser.GreetingContext ctx ) {
    System.out.println( "Entering greeting : " + ctx.ID().getText() );
  }

  $$@Override
  public void exitGreeting(HelloParser.GreetingContext ctx ) {
    System.out.println( "Exiting greeting" );
  }
}