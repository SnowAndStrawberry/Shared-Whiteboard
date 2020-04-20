
import javax.swing.JTextArea;

public class Message {
	public static String inputText, outputText;
    public static void sendMessage(JTextArea input,JTextArea output) {
    	inputText = input.getText();
    	outputText = output.getText();
    	if(outputText == null) {
    		outputText = inputText;
    	}else{
    	    outputText= outputText + "\n" + inputText;
    	}
	    output.setText(outputText);
	    input.setText(null);
    }
}
