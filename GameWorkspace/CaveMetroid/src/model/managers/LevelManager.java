
package model.managers;

import java.util.Set;

import model.Stage;

import view.Constants;


public class LevelManager
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	/*
	 * Stores the list of maps that are part of one level.
	 */
	Set < Stage >	levelStages;
	Stage		startStage;
	Stage		currStage;

	public LevelManager ( )
	{
		
	}
}