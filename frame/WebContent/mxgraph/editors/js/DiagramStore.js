/**
 * $Id: DiagramStore.js,v 1.1 2009/09/25 09:51:33 gaudenz Exp $
 * Copyright (c) 2006-2009, JGraph Ltd
 *
 * Class: DiagramStore
 * 
 * A class for storing diagrams. This implementation uses
 * Google Gears.
 */
var DiagramStore =
{

	/**
	 * Variable: diagrams
	 * 
	 * Array for in-memory storage of the diagrams. This is not persistent
	 * across multiplace invocations and is only used as a fallback if no
	 * client- or server-side storage is available.
	 */
	diagrams: new Object(),

	/**
	 * Variable: diagrams
	 * 
	 * Array for in-memory storage of the diagrams. This is not persistent
	 * across multiplace invocations and is only used as a fallback if no
	 * client- or server-side storage is available.
	 */
	eventSource: new mxEventSource(this),

	/**
	 * Function: init
	 * 
	 * Initializes the diagram store. This is invoked at class creation time
	 * and returns the db instance to operate on.
	 */
	db: function()
	{
		var db = null;
		
		try
		{					
			db = google.gears.factory.create('beta.database', '1.0');
			db.open('mxGraphEditor');
			db.execute('CREATE TABLE IF NOT EXISTS Diagrams ('+
				'NAME PRIMARY KEY,'+
				'XML TEXT' +
				');');
			
			return db;
		}
		catch (e)
		{
			// ignore
		}
		
		return db;
	}(),
	
	/**
	 * Function: addListener
	 */
	addListener: function(name, funct)
	{
		DiagramStore.eventSource.addListener(name, funct);
	},

	/**
	 * Function: removeListener
	 */
	removeListener: function(funct)
	{
		DiagramStore.eventSource.removeListener(funct);
	},

	/**
	 * Function: put
	 * 
	 * Puts the given diagram into the store, replacing any existing diagram
	 * for the given name.
	 */
	put: function(name, xml)
	{
		if (DiagramStore.db != null)
		{
			DiagramStore.db.execute('DELETE FROM Diagrams WHERE name = ?;', [name]);
			DiagramStore.db.execute('INSERT INTO Diagrams (NAME, XML) VALUES (?, ?);', [name, xml]);
		}
		else
		{
			DiagramStore.diagrams[name] = xml;
		}
		
		DiagramStore.eventSource.fireEvent('put');
	},

	/**
	 * Function: remove
	 * 
	 * Removes the given diagram from the store and returns
	 */
	remove: function(name)
	{
		if (DiagramStore.db != null)
		{
			DiagramStore.db.execute('DELETE FROM Diagrams WHERE name = ?;', [name]);
		}
		else
		{
			delete DiagramStore.diagrams[name];
		}

		DiagramStore.eventSource.fireEvent('remove');
	},

	/**
	 * Function: get
	 * 
	 * Returns the given diagram from the store or null of no such diagram
	 * can be found.
	 */
	get: function(name)
	{
		var xml = null;
		
		if (DiagramStore.db != null)
		{
			var rs = DiagramStore.db.execute('SELECT xml FROM Diagrams WHERE NAME = ?;', [name]);
			
			if (rs.isValidRow())
			{
				xml = rs.field(0);
			}
			
			rs.close();
		}
		else
		{
			xml = DiagramStore.diagrams[name];
		}
		
		return xml; 
	},

	/**
	 * Function: getNames
	 * 
	 * Returns all diagram names in the store as an array.
	 */
	getNames: function(name)
	{
		var names = new Array();
		
		if (DiagramStore.db != null)
		{
			var rs = DiagramStore.db.execute('SELECT name FROM Diagrams;');
	
			while (rs.isValidRow())
			{
				names.push(rs.field(0));
				rs.next();
			}
			
			rs.close();
		}
		else
		{
		    for (var name in DiagramStore.diagrams)
		    {
		    	names.push(name);
		    }
		}
	    
	    return names;
	}

};
