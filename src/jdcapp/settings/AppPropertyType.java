package jdcapp.settings;

/**
 * This enum provides properties that are to be loaded via
 * XML files to be used for setting up the application.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public enum AppPropertyType {
    
        //TODO: Add all necessary types for app properties that will be loaded in (hasn't been changed yet)
    
        // LOADED FROM jdc_app_properties.xml
        APP_TITLE,
	APP_LOGO,
	APP_CSS,
	APP_PATH_CSS,
        
        // FILE TOOLBAR ICONS
        NEW_ICON,
        LOAD_ICON,
        SAVE_ICON,
	SAVE_AS_ICON,
        PHOTO_EXPORT_ICON,
        CODE_EXPORT_ICON,
        EXIT_ICON,
        
        // TOOLTIPS FOR FILE TOOLBAR BUTTONS
        NEW_TOOLTIP,
        LOAD_TOOLTIP,
        SAVE_TOOLTIP,
	SAVE_AS_TOOLTIP,
	PHOTO_EXPORT_TOOLTIP,
        CODE_EXPORT_TOOLTIP,
        EXIT_TOOLTIP,
        
        // EDIT TOOLBAR ICONS
        SELECT_ICON,
        RESIZE_ICON,
        ADD_CLASS_ICON,
        ADD_INTERFACE_ICON,
        REMOVE_ICON,
        UNDO_ICON,
        REDO_ICON,
        
        // TOOLTIPS FOR EDIT TOOLBAR BUTTONS
        SELECT_TOOLTIP,
        RESIZE_TOOLTIP,
        ADD_CLASS_TOOLTIP,
        ADD_INTERFACE_TOOLTIP,
        REMOVE_TOOLTIP,
        UNDO_TOOLTIP,
        REDO_TOOLTIP,
        
        // VIEW TOOLBAR ICONS
        ZOOM_IN_ICON,
        ZOOM_OUT_ICON,
        
        // TOOLTIPS/LABELS FOR VIEW TOOLBAR BUTTONS
        ZOOM_IN_TOOLTIP,
        ZOOM_OUT_TOOLTIP,
        GRID_TOGGLE_LABEL,
        SNAP_TOGGLE_LABEL,
	
	// ERROR MESSAGES
	NEW_ERROR_MESSAGE,
	LOAD_ERROR_MESSAGE,
	SAVE_ERROR_MESSAGE,
	PROPERTIES_LOAD_ERROR_MESSAGE,
	
	// ERROR TITLES
	NEW_ERROR_TITLE,
	LOAD_ERROR_TITLE,
	SAVE_ERROR_TITLE,
	PROPERTIES_LOAD_ERROR_TITLE,
	
	// AND VERIFICATION MESSAGES AND TITLES
        NEW_COMPLETED_MESSAGE,
	NEW_COMPLETED_TITLE,
        LOAD_COMPLETED_MESSAGE,
	LOAD_COMPLETED_TITLE,
        SAVE_COMPLETED_MESSAGE,
	SAVE_COMPLETED_TITLE,	
	SAVE_UNSAVED_WORK_TITLE,
        SAVE_UNSAVED_WORK_MESSAGE,
	
	SAVE_WORK_TITLE,
	LOAD_WORK_TITLE,
	WORK_FILE_EXT,
	WORK_FILE_EXT_DESC,
	PROPERTIES_
}