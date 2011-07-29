package net.bitdixit.ywu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class InformationActivity extends Activity {
	public String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		WebView mWebView = (WebView) findViewById(R.id.infoWebView);
		String content;
		try {
			content = convertStreamToString(getResources().getAssets()
						.open("information.html"));
		} catch (Exception e) {
			content = "<html><body>Cannot load info:"+e.getMessage()+"</body></html>";
		}
		mWebView.loadData(content,"text/html", "utf-8");
	}
}
