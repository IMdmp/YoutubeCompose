package com.imdmp.youtubecompose.features.profile

import android.provider.ContactsContract
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen() {
    Text("Profile")
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}

/*

//related items:
public static RelatedItemInfo getInfo(final StreamInfo info) {
        final ListLinkHandler handler = new ListLinkHandler(
                info.getOriginalUrl(), info.getUrl(), info.getId(), Collections.emptyList(), null);
        final RelatedItemInfo relatedItemInfo = new RelatedItemInfo(
                info.getServiceId(), handler, info.getName());
        final List<InfoItem> xrelatedItems = new ArrayList<>(info.getRelatedItems());
        relatedItemInfo.setRelatedItems(relatedItems);
        return relatedItemInfo;
    }

    //get comments:
        checkServiceId(serviceId);
        return checkCache(forceLoad, serviceId, url, InfoItem.InfoType.COMMENT,
                Single.fromCallable(() ->
                        CommentsInfo.getInfo(NewPipe.getService(serviceId), url)));

    //get video playing info:
        val streamInfo = StreamInfo.getInfo(NewPipe.getService(0), encryptedStreamUrl)

 */
