package finalProject;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GameWindow extends Frame implements ActionListener {

	private static final long serialVersionUID = 1L;

	AudioClip sound;
	private Button playSound, loopSound, stopSound;

	URL base;

	Container c;

	public GameWindow(String title, URL base) {
		super(title); // Set the title
		// setSize(600, 200); // Set size to the frame
		setVisible(true); // Make the frame visible
		setBackground(Color.black); // Set the background

		c = new Container();
		c.setSize(600, 200);
		c.setVisible(true);
		c.setLayout(new FlowLayout());

		this.base = base;

		playSound = new Button("Play");
		playSound.setBounds(100, 0, 10, 10);
		playSound.addActionListener(this);
		c.add(playSound);

		loopSound = new Button("Loop");
		loopSound.setBounds(0, 0, 10, 10);
		loopSound.addActionListener(this);
		c.add(loopSound);

		stopSound = new Button("Stop");
		stopSound.setBounds(50, 0, 10, 10);
		stopSound.addActionListener(this);
		c.add(stopSound);

		add(c);

		pack();

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playSound)
			sound.play();
		else if (e.getSource() == loopSound)
			sound.loop();
		else if (e.getSource() == stopSound)
			sound.stop();

	}

}
