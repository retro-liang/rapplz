package com.retro.rapplz.server.datastore.dao;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.retro.rapplz.server.datastore.entity.Account;
import com.retro.rapplz.server.datastore.entity.Activity;
import com.retro.rapplz.server.datastore.entity.App;
import com.retro.rapplz.server.datastore.entity.AppCommentIndex;
import com.retro.rapplz.server.datastore.entity.AppCompetitorIndex;
import com.retro.rapplz.server.datastore.entity.AppIndex;
import com.retro.rapplz.server.datastore.entity.AppLike;
import com.retro.rapplz.server.datastore.entity.AppLikeIndex;
import com.retro.rapplz.server.datastore.entity.AppReviewIndex;
import com.retro.rapplz.server.datastore.entity.AppTag;
import com.retro.rapplz.server.datastore.entity.AppTagIndex;
import com.retro.rapplz.server.datastore.entity.Profile;
import com.retro.rapplz.server.datastore.entity.Role;
import com.retro.rapplz.server.datastore.entity.Status;
import com.retro.rapplz.server.datastore.entity.User;
import com.retro.rapplz.server.datastore.entity.UserIndex;
import com.retro.rapplz.server.datastore.entity.UserMessage;
import com.retro.rapplz.server.datastore.entity.UserMessageBox;

public class DAO extends DAOBase
{
	static
	{
		ObjectifyService.register(User.class);
		ObjectifyService.register(Account.class);
		ObjectifyService.register(Profile.class);
		ObjectifyService.register(Role.class);
		ObjectifyService.register(Status.class);
		ObjectifyService.register(App.class);
		ObjectifyService.register(AppIndex.class);
		ObjectifyService.register(AppTag.class);
		ObjectifyService.register(AppTagIndex.class);
		ObjectifyService.register(AppLike.class);
		ObjectifyService.register(AppLikeIndex.class);
		ObjectifyService.register(AppCompetitorIndex.class);
		ObjectifyService.register(Activity.class);
		ObjectifyService.register(AppCommentIndex.class);
		ObjectifyService.register(AppReviewIndex.class);
		ObjectifyService.register(UserIndex.class);
		ObjectifyService.register(UserMessage.class);
		ObjectifyService.register(UserMessageBox.class);
	}
}