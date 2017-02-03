/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package model;

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.FavoriteBean;

public class FavoriteDAO extends GenericDAO<FavoriteBean> {

	public FavoriteDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(FavoriteBean.class, tableName, cp);
	}
	
    public FavoriteBean[] getUserFavorites(int userId) throws RollbackException {
    	try {
			Transaction.begin();
			
    		FavoriteBean[] favoriteBeans = match(MatchArg.equals("userId", userId));

			Arrays.sort(favoriteBeans, (FavoriteBean i1, FavoriteBean i2) -> -i1.getClickCount()+
	    			i2.getClickCount());
			
			Transaction.commit();
			
			return favoriteBeans;
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
    	
    }
    
    public void incrementClickCount(int favoriteId) throws RollbackException {
    	try {
			Transaction.begin();
			
			FavoriteBean selectedBean = match(MatchArg.equals("favoriteId", favoriteId))[0];
			selectedBean.setClickCount(selectedBean.getClickCount()+1);
			update(selectedBean);
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
    }
}
