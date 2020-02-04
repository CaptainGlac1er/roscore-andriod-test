package com.georgecolgrove.test.testroscore;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.ros.internal.node.client.MasterClient;
import org.ros.internal.node.response.Response;
import org.ros.internal.node.xmlrpc.XmlRpcTimeoutException;
import org.ros.namespace.GraphName;

import java.net.URI;
import java.net.URISyntaxException;

public class TestConnect extends AsyncTask<String, Void, String> {
    /**
     * Lookup text for catching a ConnectionException when attempting to
     * connect to a master.
     */
    private static final String CONNECTION_EXCEPTION_TEXT = "ECONNREFUSED";

    /**
     * Lookup text for catching a UnknownHostException when attemping to
     * connect to a master.
     */
    private static final String UNKNOW_HOST_TEXT = "UnknownHost";
    private Context context;
    TestConnect(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URI test = new URI(params[0]);
            MasterClient masterClient = new MasterClient(test);
            Response response = masterClient.getUri(GraphName.of("android/master_chooser_activity"));
            return "Connected!";
        } catch (URISyntaxException e) {
            return "Invalid URI.";
        } catch (XmlRpcTimeoutException e) {
            return "Master unreachable!";
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            if (exceptionMessage.contains(CONNECTION_EXCEPTION_TEXT))
                return "Unable to communicate with master!";
            else if (exceptionMessage.contains(UNKNOW_HOST_TEXT))
                return "Unable to resolve URI hostname!";
            else
                return "Communication error!";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }
}
