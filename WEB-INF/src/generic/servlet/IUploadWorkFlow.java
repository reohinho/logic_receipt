/**
 * use by AppFileUploadServlet and child class
 */
package generic.servlet;

public interface IUploadWorkFlow 
{
    public static final String MAX_FILE_SIZE = "max_file_size";
    public static final String MAX_THRESHOLD_SIZE = "max_threshold_size";
    public static final String TMP_DIRECTORY = "tmp_directory";
    public static final String OBJECT_INDEX = "object_index";
    public static final String UPLOAD_FILE_ID = "upload_file_id";

    public static final String MESSAGE = "message";
    public static final String UPLOAD_FILENAME = "upload_filename";
    public static final String UPLOAD_FILETYPE = "upload_filetype";
    public static final String DATA_BEAN = "data_bean";    
    public static final String ACTION = "action";        
}