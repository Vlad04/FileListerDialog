package com.nextline.fileslister;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.filelister.R;
import com.squareup.otto.Subscribe;

import java.io.File;

import static android.content.DialogInterface.BUTTON_NEUTRAL;

/**
 * A File Lister Dialog
 */

public class FileListerDialog {

    /**
     * File Filter for the FileListerDialog
     */
    public enum FILE_FILTER {
        /**
         * List All Files
         */
        ALL_FILES,
        /**
         * List only directories
         */
        DIRECTORY_ONLY,
        /**
         * List Directory and Image files
         */
        IMAGE_ONLY,
        /**
         * List Directory and Video files
         */
        VIDEO_ONLY,
        /**
         * List Directory and Audio files
         */
        AUDIO_ONLY
    }

    private AlertDialog alertDialog;

    private FilesListerView filesListerView;

    private OnFileSelectedListener onFileSelectedListener;

    private Boolean bus = false;

    private FileListerDialog(@NonNull Context context) {
        //super(context);
        alertDialog = new AlertDialog.Builder(context).create();
        init(context);
    }

    private FileListerDialog(@NonNull Context context, int themeResId) {
        //super(context, themeResId);
        alertDialog = new AlertDialog.Builder(context, themeResId).create();
        init(context);
    }

    /**
     * Creates a default instance of FileListerDialog
     *
     * @param context Context of the App
     * @return Instance of FileListerDialog
     */
    public static FileListerDialog createFileListerDialog(@NonNull Context context) {
        return new FileListerDialog(context);
    }

    /**
     * Creates an instance of FileListerDialog with the specified Theme
     *
     * @param context Context of the App
     * @param themeId Theme Id for the dialog
     * @return Instance of FileListerDialog
     */
    public static FileListerDialog createFileListerDialog(@NonNull Context context, int themeId) {
        return new FileListerDialog(context, themeId);
    }

    private void init(Context context) {
        filesListerView = new FilesListerView(context);
        alertDialog.setView(filesListerView);
        /*alertDialog.setButton(BUTTON_POSITIVE, "Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (onFileSelectedListener != null)
                    onFileSelectedListener.onFileSelected(filesListerView.getSelected(), filesListerView.getSelected().getAbsolutePath());
            }
        });*/
        alertDialog.setButton(BUTTON_NEUTRAL, context.getText(R.string.cancel_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        /*alertDialog.setButton(BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });*/
    }

    /**
     * Display the FileListerDialog
     */
    public void show() {
        //getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        switch (filesListerView.getFileFilter()) {
            case DIRECTORY_ONLY:
                alertDialog.setTitle(R.string.selectDirectory_text);
                break;
            case VIDEO_ONLY:
                alertDialog.setTitle(R.string.selectVideo_text);
                break;
            case IMAGE_ONLY:
                alertDialog.setTitle(R.string.selectImage_text);
                break;
            case AUDIO_ONLY:
                alertDialog.setTitle(R.string.selectAudio_text);
                break;
            case ALL_FILES:
                alertDialog.setTitle(R.string.selectFile_text);
                break;
            default:
                alertDialog.setTitle(R.string.selectFile_text);
        }
        filesListerView.start();
        alertDialog.show();

        GlobalBus.getBus().register(this);

        /*alertDialog.getButton(BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filesListerView.goToDefaultDir();
            }
        });*/
    }

    @Subscribe
    public void dismiss(Events.Selected message){
        alertDialog.dismiss();
        /*if (onFileSelectedListener != null)
            onFileSelectedListener.onFileSelected(filesListerView.getSelected(), filesListerView.getSelected().getAbsolutePath());*/
        System.out.println("====================ENTRO " + bus);
        if (onFileSelectedListener != null && !bus) {
            System.out.println("====================ENTRO MESSAGE " + bus);
            bus = true;
            onFileSelectedListener.onFileSelected(filesListerView.getSelected(), filesListerView.getSelected().getAbsolutePath());
        }
    }

    /**
     * Listener to know which file/directory is selected
     *
     * @param onFileSelectedListener Instance of the Listener
     */
    public void setOnFileSelectedListener(OnFileSelectedListener onFileSelectedListener) {
        this.onFileSelectedListener = onFileSelectedListener;
    }

    /**
     * Set the initial directory to show the list of files in that directory
     *
     * @param file File pointing to the directory
     */
    public void setDefaultDir(@NonNull File file) {
        filesListerView.setDefaultDir(file);
    }

    /**
     * Set the initial directory to show the list of files in that directory
     *
     * @param file String denoting to the directory
     */
    public void setDefaultDir(@NonNull String file) {
        filesListerView.setDefaultDir(file);
    }

    /**
     * Set the file filter for listing the files
     *
     * @param fileFilter One of the FILE_FILTER values
     */
    public void setFileFilter(FILE_FILTER fileFilter) {
        filesListerView.setFileFilter(fileFilter);
    }

}
