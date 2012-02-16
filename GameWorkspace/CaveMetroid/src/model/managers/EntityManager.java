
package model.managers;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import log.Log;
import model.GameState;
import model.entities.Entity;
import model.entities.ViewableEntity;
import model.time.GameTime;
import controller.input.InputChange;


public class EntityManager
{

	private Map < Class < ? extends Entity > , Set < ? extends Entity >>	byType;



	public void update ( GameTime gameTime, InputChange inputChange, GameState gameState )
	{
		LinkedList < Entity > toRemove = new LinkedList < Entity > ( );

		Log.v ( "Entity Set Info", "Current Entity Count = " + allOfType ( Entity.class ).size ( ) );

		for (Entity e : allOfType ( Entity.class ))
		{
			e.update ( gameTime, inputChange, gameState );
			if ( e.shouldDestroy ( ) )
				toRemove.add ( e );
		}

		for (Entity e : toRemove)
		{
			destroyEntity ( e );
		}
	}



	@SuppressWarnings ( { "unchecked" } )
	public < T extends Entity > Set < T > allOfType ( Class < T > classType )
	{
		Set < T > set = (Set < T >) byType.get ( classType );
		if ( set == null )
		{
			set = new LinkedHashSet < T > ( );
			byType.put ( classType, set );
		}
		return set;
	}



	@SuppressWarnings ( { "unchecked", "rawtypes" } )
	public < T extends Entity > T createEntity ( Class < T > classType, Object... parameters )
	{

		T entity = null;
		if ( parameters != null && parameters.length > 0 )
		{
			Class < ? > [] parameterTypes = typesOf ( parameters );

			Constructor < T > constructor = null;
			try
			{
				constructor = classType.getConstructor ( parameterTypes );
			}
			catch ( NoSuchMethodException e )
			{
				Log.e ( "Constructor Finding Error", "Cannot find constructor in " + classType.toString ( ) + " with params " + Arrays.toString ( parameterTypes ) );
				return null;
			}

			try
			{
				entity = constructor.newInstance ( parameters );
			}
			catch ( Exception e )
			{
				Log.e ( "Constructor Calling Error", "Cannot call constructor in " + classType.toString ( ) + " with params " + Arrays.toString ( parameterTypes ) );
				return null;
			}
		}
		else
		{
			try
			{
				entity = classType.newInstance ( );
			}
			catch ( Exception e )
			{
				Log.e ( "Constructor Finding Error", "Cannot find constructor in " + classType.toString ( ) + " with no params." );
				return null;
			}
		}

		if ( entity == null )
		{
			Log.e ( "Initialization Error", classType + " could not be initialized." );
		}

		Set < Class > interfaces = new HashSet < Class > ( );

		for (Class c = entity.getClass ( ); c != Object.class; c = c.getSuperclass ( ))
		{
			interfaces.addAll ( interfaceDecendenceOf ( c ) );
			allOfType ( c ).add ( entity );
		}

		for (Class c : interfaces)
		{
			allOfType ( c ).add ( entity );
		}

		Log.v ( "Current Entity Map", byType );

		return entity;
	}



	@SuppressWarnings ( { "rawtypes" } )
	private Class [] typesOf ( Object [] parameters )
	{
		Class [] types = new Class [ parameters.length ];
		for (int i = 0; i < parameters.length; i++ )
			types[ i ] = parameters[ i ].getClass ( );

		return types;
	}



	private Set < Class < ? extends Entity > > interfaceDecendenceOf ( Class < ? extends Entity > classType )
	{
		if ( classType.getInterfaces ( ).length == 0 )
			return new HashSet < Class < ? extends Entity > > ( );

		Set < Class < ? extends Entity > > interfaces = new LinkedHashSet < Class < ? extends Entity > > ( );

		for (Class < ? > inheritedInterface : classType.getInterfaces ( ))
		{
			@SuppressWarnings ( "unchecked" )
			Class < ? extends Entity > inherited = (Class < ? extends Entity >) inheritedInterface;
			interfaces.add ( inherited );
			interfaces.addAll ( interfaceDecendenceOf ( inherited ) );
		}

		return interfaces;
	}



	@SuppressWarnings ( { "unchecked", "rawtypes" } )
	public < T extends Entity > void destroyEntity ( T entity )
	{
		for (Class c = entity.getClass ( ); c != Object.class; c = c.getSuperclass ( ))
		{
			allOfType ( c ).remove ( entity );
		}
	}



	public EntityManager ( )
	{
		byType = new HashMap < Class < ? extends Entity > , Set < ? extends Entity > > ( );
		byType.put ( ViewableEntity.class, new TreeSet < ViewableEntity > ( ViewableEntity.viewableEntityComparator ) );
	}



	public < T extends Entity > T getSingleton ( Class < T > type )
	{
		List < T > typeObjs = new ArrayList < T > ( allOfType ( type ) );

		if ( typeObjs.size ( ) != 1 )
		{
			Log.wtf ( type.getSimpleName ( ) + " Count Error", "There are " + typeObjs.size ( ) + " of them!" );
			return null;
		}

		return typeObjs.get ( 0 );
	}
}