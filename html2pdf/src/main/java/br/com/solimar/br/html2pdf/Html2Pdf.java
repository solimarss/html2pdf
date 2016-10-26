package br.com.solimar.br.html2pdf;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

public class Html2Pdf {

	public static void main(String[] args) {
		OutputStream os;
		try {
			
			//String content = readFile("C:\\Users\\66927110291\\Documents\\Declaracao-13-Salario.html", StandardCharsets.UTF_8);
			String content = readFile("C:\\Users\\66927110291\\Documents\\Declaracao-13-Salario.html", StandardCharsets.ISO_8859_1);
			os = new FileOutputStream("C:\\Users\\66927110291\\Documents\\Declaracao-13-Salario.pdf");
			Html2Pdf.convert(content, os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	static String readFile(String path, Charset encoding) throws IOException 
	{
	    byte[] encoded = Files.readAllBytes(Paths.get(path));
	    return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	
	public static void convert(String input, OutputStream out) throws DocumentException {
		convert(new ByteArrayInputStream(input.getBytes()), out);
	}

	public static void convert(InputStream input, OutputStream out) throws DocumentException {
		Tidy tidy = new Tidy();
		Document doc = tidy.parseDOM(input, null);
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(doc, null);
		renderer.layout();
		renderer.createPDF(out);
	}

}
