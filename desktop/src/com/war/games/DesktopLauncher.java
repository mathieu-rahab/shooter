package com.war.games;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.war.games.screens.GameShooter;



public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(800, 460);
		// Définir la taille minimale de la fenêtre
		config.setWindowSizeLimits(800, 460, -1, -1);
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setTitle("Shooter by war games");
		config.setWindowIcon("elt/playerShips/playerShip1_green.png");

		new Lwjgl3Application(new GameShooter(), config);
	}
}

/*
import com.badlogic.gdx.Graphics.DisplayMode;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// Obtenez le mode d'affichage de l'écran actuel
		DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();

		// Configurez l'application pour utiliser le mode plein écran
		config.setFullscreenMode(displayMode);

		// Autres configurations (optionnelles)
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setTitle("Shooter by war games");
		config.setWindowIcon("elt/playerShips/playerShip1_green.png");

		new Lwjgl3Application(new GameShooter(), config);
	}
}*/
