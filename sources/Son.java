import javax.swing.*;
public class Son {
    
    private java.net.URL url ;
    private java.applet.AudioClip sound ;
    
    public Son(String Titre) {
    seturl(Titre);
    }
    
    public java.net.URL geturl()
    {return url ;}
    
    public java.applet.AudioClip getSon()
    {return sound ;}
    
    public void seturl(String Titre) {
    try
    {url = new java.net.URL(getClass().getResource("Audio/"), Titre);
     sound = java.applet.Applet.newAudioClip(url) ;
    }
    catch (java.net.MalformedURLException e)
    {System.err.println("Impossible d'obtenir le fichier");}
    }
    
    public void playSon()
    {sound.play() ;}
    
    public void loopSon()
    {sound.loop() ;}
    
    public void stopSon()
    {sound.stop() ;}
}
