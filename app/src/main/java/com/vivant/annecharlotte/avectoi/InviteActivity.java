package com.vivant.annecharlotte.avectoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class InviteActivity extends AppCompatActivity {

    private static final String TAG = "InviteActivity";
    private Button inviteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        inviteBtn = findViewById(R.id.invite_btn);
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLink();
            }
        });
    }

    private void createLink() {
        Log.e(TAG, "createLink: ");
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.lesretoques.com/"))
                .setDomainUriPrefix("https://supertoi.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        Log.d(TAG, "createLink: dynamicLink " + dynamicLinkUri.toString());

        //https://supertoi.page.link?apn=com.vivant.annecharlotte.avectoi&link=https%3A%2F%2Fwww.lesretoques.com%2F

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink( dynamicLinkUri)
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Log.d(TAG, "onComplete: shortLink " + shortLink.toString());

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);

                        } else {
                            // Error
                            Log.e(TAG, "onComplete shortLink: error " +task.getException());
                        }
                    }
                });
    }
}
