package com.dialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.project1.R;

public class UpdateDialog extends Dialog{

	public UpdateDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public UpdateDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private View contentView;
		public Builder(Context context) {
			this.context = context;
		}



		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public UpdateDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final UpdateDialog dialog = new UpdateDialog(context,R.style.Dialog);
			dialog.setCanceledOnTouchOutside(false);
			View layout = inflater.inflate(R.layout.dialog_update_layout, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set back listener
			((ImageView)layout.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			dialog.setContentView(layout);
			return dialog;
		}

	}

}
