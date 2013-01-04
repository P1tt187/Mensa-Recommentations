package com.fhs.mensa.datastorage;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import timeoperations.CronWorker;

import com.fhs.mensa.datastorage.dataclasses.Foot;
import com.fhs.mensa.datastorage.dataclasses.User;
import com.fhs.mensa.datastorage.dataclasses.UserChoise;

public class DatabaseOperations {
	
	@SuppressWarnings("unchecked")
	public static final List<Foot> getFoot(Session session) {
		List<Foot> result = (List<Foot>) session.createCriteria(Foot.class).add(Restrictions.eq("activeDay", CronWorker.getCurrentDate())).list();
		System.out.println(result);
		return result;
	}
	
	public static final Float getMeanVotesForFoot(Session session, Foot foot) {
		@SuppressWarnings("unchecked")
		List<UserChoise> choises = session.createCriteria(UserChoise.class).add(Restrictions.eq("foot", foot)).list();
		// new LinkedList<>();
		// //session.createCriteria(UserChoise.class).list();
		
		if (choises.size() == 0) {
			return 0f;
		}
		Float result = 0f;
		for (UserChoise e : choises) {
			result += e.getVote();
		}
		return result / new Float(choises.size());
	}
	
	public static final UserChoise getUserChoiseForFoot(Session session, User user, Foot foot) {
		
		return (UserChoise) session.createCriteria(UserChoise.class)
		        .add(Restrictions.and(Restrictions.eq("user.id", user.getId()), Restrictions.eq("foot.id", foot.getId()))).uniqueResult();
	}
	
	public static final Foot getFootById(Session session, Long footId) {
		
		return (Foot) session.createCriteria(Foot.class).add(Restrictions.idEq(footId)).uniqueResult();
	}
	
	public static final User getUserByUserID(Session session, String userID) {
		return (User) session.createCriteria(User.class).add(Restrictions.eq("userID", userID)).uniqueResult();
	}
	
	private DatabaseOperations() {
		// TODO Auto-generated constructor stub
	}
	
}
