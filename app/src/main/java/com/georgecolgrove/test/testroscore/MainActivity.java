package com.georgecolgrove.test.testroscore;

import android.os.Bundle;

import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.ros.address.InetAddressFactory;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    /**
     * Default port number for master URI. Apended if the URI does not
     * contain a port number.
     */
    private static final int DEFAULT_PORT = 11311;

    private TextView uriText;
    protected HashMap<String, NodeMain> nodes;
    private RecyclerView nodeList;
    NodeCommander nodeCommander;

    public MainActivity() {
        super();
        nodes = new HashMap<>();
        nodeCommander = new NodeCommander(DefaultNodeMainExecutor.newDefault());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        uriText = findViewById(R.id.input_url);
        nodeList = findViewById(R.id.nodes);

        // View to apply Snackbar to
        View view = findViewById(R.id.connect);

        // Add Nodes here
        nodes.put("/chatter Publisher", new MyNode());
        nodes.put("/chatter Listener", new MyListenerNode(view));


        MyAdapter myAdapter = new MyAdapter(this, nodes, nodeCommander);
        nodeList.setAdapter(myAdapter);
        nodeList.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getDefaultHostAddress() {
        return InetAddressFactory.newNonLoopback().getHostAddress();
    }

    public void connectToMaster(View view) {
        String uri = uriText.getText().toString();
        try {
            nodeCommander.addConfiguration(NodeConfiguration.newPublic(this.getDefaultHostAddress(), new URI(uri)));
        } catch (URISyntaxException uriSyntaxException) {
            System.out.println(uriSyntaxException.toString());
        }

    }
}
