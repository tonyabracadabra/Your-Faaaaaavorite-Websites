/**
 * J2EE 08672
 *
 * @author Xupeng Tong
 * Date:   2016/12/14
 */

package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("favoriteId")
public class FavoriteBean {

	private int favoriteId;
	private int userId;
	private String URL;
	private String comment;
	private int clickCount;
	
	// default constructor
	public FavoriteBean() {
		this.clickCount = 0;
	}
	
	// the constructor I use!
	public FavoriteBean(int userId, String URL, String comment) {
		this.userId = userId;
		this.URL = URL;
		this.comment = comment;
		this.clickCount = 0;
	}
	
	public int getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(int favoriteId) {
		this.favoriteId = favoriteId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

}
