package sample;

import static rescuecore2.misc.java.JavaTools.instantiate;

import rescuecore2.messages.control.KVTimestep;
import rescuecore2.misc.CommandLineOptions;
import rescuecore2.view.ViewComponent;
import rescuecore2.view.ViewListener;
import rescuecore2.view.RenderedObject;
import rescuecore2.score.ScoreFunction;
import rescuecore2.Constants;
import rescuecore2.Timestep;
import rescuecore2.config.Config;
import rescuecore2.config.ConfigException;
import rescuecore2.connection.TCPConnection;
import rescuecore2.log.Logger;
import rescuecore2.standard.view.AnimatedWorldModelViewer;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.dom4j.DocumentException;

import AnimFireChalBuilding.AnimFireChal;
import AnimFireChalBuilding.Resources;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.List;
import java.text.NumberFormat;

import rescuecore2.standard.components.StandardViewer;

/**
   A simple viewer.
 */
public class SampleViewer extends StandardViewer {
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final int PRECISION = 3;

    private static final String FONT_SIZE_KEY = "viewer.font-size";
    private static final String MAXIMISE_KEY = "viewer.maximise";
    private static final String TEAM_NAME_KEY = "viewer.team-name";

    private ScoreFunction scoreFunction;
    private ViewComponent viewer;
    private JLabel timeLabel;
    private JLabel scoreLabel;
    private JLabel teamLabel;
    private JLabel mapLabel;
    private NumberFormat format;
    private Server server;

    @Override
    protected void postConnect() {
        super.postConnect();
        int fontSize = config.getIntValue(FONT_SIZE_KEY, DEFAULT_FONT_SIZE);
        String teamName = config.getValue(TEAM_NAME_KEY, "");
        scoreFunction = makeScoreFunction();
        format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(PRECISION);
        JFrame frame = new JFrame("Viewer " + getViewerID() + " (" + model.getAllEntities().size() + " entities)");
        viewer = new AnimatedWorldModelViewer();
        viewer.initialise(config);
        viewer.view(model);
        // CHECKSTYLE:OFF:MagicNumber
        viewer.setPreferredSize(new Dimension(500, 500));
        // CHECKSTYLE:ON:MagicNumber
        timeLabel = new JLabel("Time: Not started", JLabel.CENTER);
        teamLabel = new JLabel(teamName, JLabel.CENTER);
        scoreLabel = new JLabel("Score: Unknown", JLabel.CENTER);
        String mapdir=config.getValue("gis.map.dir").trim();
        
		String[] map_spl = mapdir.split("/");
		int index = map_spl.length-1;
		String mapname = map_spl[index].trim();
		if(mapname.equals(""))
			mapname = map_spl[--index].trim();
		if(mapname.equals("map"))
			mapname = map_spl[--index].trim();
			
        
        String totalTime = config.getValue("kernel.timesteps");
        int channelCount=config.getIntValue("comms.channels.count")-1;//-1 for say
        
        mapLabel=new JLabel(mapname+" ("+totalTime+") | "+(channelCount==0? "No Comm":channelCount+" channels"), JLabel.CENTER);
        timeLabel.setBackground(Color.WHITE);
        timeLabel.setOpaque(true);
        timeLabel.setFont(timeLabel.getFont().deriveFont(Font.PLAIN, fontSize));
        teamLabel.setBackground(Color.WHITE);
        teamLabel.setOpaque(true);
        teamLabel.setFont(timeLabel.getFont().deriveFont(Font.PLAIN, fontSize));
        scoreLabel.setBackground(Color.WHITE);
        scoreLabel.setOpaque(true);
        scoreLabel.setFont(timeLabel.getFont().deriveFont(Font.PLAIN, fontSize));
        
        mapLabel.setBackground(Color.WHITE);
        mapLabel.setOpaque(true);
        mapLabel.setFont(timeLabel.getFont().deriveFont(Font.PLAIN, fontSize));
        
        frame.add(viewer, BorderLayout.CENTER);
        // CHECKSTYLE:OFF:MagicNumber
        JPanel labels = new JPanel(new GridLayout(1, 4));
        // CHECKSTYLE:ON:MagicNumber
        labels.add(teamLabel);
        labels.add(timeLabel);
        labels.add(scoreLabel);
        labels.add(mapLabel);
        frame.add(labels, BorderLayout.NORTH);
        frame.pack();
        if (config.getBooleanValue(MAXIMISE_KEY, false)) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        frame.setVisible(true);

        viewer.addViewListener(new ViewListener() {
                @Override
                public void objectsClicked(ViewComponent view, List<RenderedObject> objects) {
                    for (RenderedObject next : objects) {
                        System.out.println(next.getObject());
                    }
                }

                @Override
                public void objectsRollover(ViewComponent view, List<RenderedObject> objects) {
                }
            });
        
        try {
        	server = ServerBuilder.forPort(config.getIntValue("rp")).addService(new AnimFireChal()).build();
        	server.start();
			System.out.println("Server started at " + server.getPort() + "******************************************");	
//			server.awaitTermination();
		} catch (Exception e) {
			server.shutdownNow();
			System.out.println(e.getMessage());
		}
    }

    @Override
    protected void handleTimestep(final KVTimestep t) {
        super.handleTimestep(t);
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    timeLabel.setText("Time: " + t.getTime());
                    
                    if (t.getTime() == 301) {
                    	try {
                    		System.out.println("*********** Server Terminating ***********"+server.isTerminated());
                    		server.shutdown();
                    		server.awaitTermination();
	        				System.out.println("*********** Server Exiting ***********"+server.isTerminated());
							System.exit(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
//                    if(t.getTime() == 100) {
//                    	Config config = new Config();
//                        int port = config.getIntValue(Constants.KERNEL_PORT_NUMBER_KEY, Constants.DEFAULT_KERNEL_PORT_NUMBER);
//                        String host = config.getValue(Constants.KERNEL_HOST_NAME_KEY, Constants.DEFAULT_KERNEL_HOST_NAME);
//                    	TCPConnection c;
//						try {
//							c = new TCPConnection(host, port);
//	                    	c.shutdownImpl();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//                    }
                    
                    scoreLabel.setText("Score: " + format.format(scoreFunction.score(model, new Timestep(t.getTime()))));
                    Resources.setReward(Double.parseDouble(format.format(scoreFunction.score(model, new Timestep(t.getTime())))));
                    viewer.view(model, t.getCommands());
                    viewer.repaint();
                }
            });
    }

    @Override
    public String toString() {
        return "Sample viewer";
    }

    private ScoreFunction makeScoreFunction() {
        String className = config.getValue(Constants.SCORE_FUNCTION_KEY);
        ScoreFunction result = instantiate(className, ScoreFunction.class);
        result.initialise(model, config);
        return result;
    }
}
