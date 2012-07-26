/**
 * This file contains the Destination class.
 */
package org.datasift;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.datasift.pushsubscription.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Destination class represents a data Destination.
 * 
 * @author MediaSift
 * @version 0.1
 */
public class PushSubscription extends PushDefinition {
	/**
	 * Auto-generated serialization version UID.
	 */
	private static final long serialVersionUID = -8162860993374079377L;
	
	/**
	 * Hash type constants.
	 */
	public final static String HASH_TYPE_STREAM   = "stream";
	public final static String HASH_TYPE_HISTORIC = "historic";
	
	/**
	 * Status constants.
	 */
	public final static String STATUS_ACTIVE    = "active";
	public final static String STATUS_PAUSED    = "paused";
	public final static String STATUS_STOPPED   = "stopped";
	public final static String STATUS_FINISHING = "finishing";
	public final static String STATUS_FINISHED  = "finished";
	public final static String STATUS_DELETED   = "deleted";
	
	/**
	 * Order by constants.
	 */
	public final static String ORDERBY_ID = "id";
	public final static String ORDERBY_CREATED_AT = "created_at";
	public final static String ORDERBY_REQUEST_TIME = "request_time";
	
	/**
	 * Order direction constants.
	 */
	public final static String ORDERDIR_ASC = "asc";
	public final static String ORDERDIR_DESC = "desc";
	
	/**
	 * Get a push subscription by ID.
	 * 
	 * @param User   user The user who owns the subscription.
	 * @param String id   The subscription ID.
	 * @return PushSubscription
	 * @throws EAPIError
	 * @throws EAccessDenied
	 * @throws EInvalidData
	 */
	static public PushSubscription get(User user, String id) throws EAPIError, EAccessDenied, EInvalidData {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		return new PushSubscription(user, user.callAPI("push/get", params));
	}

	/**
	 * Get a list of push subscriptions in the given user's account. Limited
	 * to 100 results.Results will be returned in ascending order by creation
	 * date.
	 * 
	 * @param User user
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> list(User user) throws EInvalidData, EAPIError, EAccessDenied {
		return list(user, 1, 100);
	}
	
	/**
	 * Get a page of push subscriptions in the given user's account, where
	 * each page contains up to 20 items. Results will be returned in
	 * ascending order by creation date.
	 * 
	 * @param User user The user.
	 * @param int  page The page number to fetch.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> list(User user, int page) throws EInvalidData, EAPIError, EAccessDenied {
		return list(user, page, 20);
	}
	
	/**
	 * Get a page of push subscriptions in the given user's account, where
	 * each page contains up to per_page items. Results will be returned in
	 * ascending order by creation date.
	 * 
	 * @param User user     The user.
	 * @param int  page     The page number to fetch.
	 * @param int  per_page The number of items per page.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> list(User user, int page, int per_page) throws EInvalidData, EAPIError, EAccessDenied {
		return list(user, page, per_page, ORDERBY_CREATED_AT, ORDERDIR_ASC, false);
	}
	
	/**
	 * Get a page of push subscriptions in the given user's account, where
	 * each page contains up to per_page items. Results will be ordered
	 * according to the supplied ordering parameters.
	 * 
	 * @param User user                The user.
	 * @param int  page                The page number to fetch.
	 * @param int  per_page            The number of items per page.
	 * @param String order_by          The field on which to order the results.
	 * @param String order_dir         The direction of the ordering.
	 * @param boolean include_finished True to include subscriptions against
	 *                                 finished historic queries.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> list(User user, int page, int per_page, String order_by, String order_dir, boolean include_finished) throws EInvalidData, EAPIError, EAccessDenied {
		return list(user, null, null, page, per_page, order_by, order_dir, include_finished);
	}
	
	/**
	 * Get a list of push subscriptions for the given stream hash. Limited
	 * to 100 results.Results will be returned in ascending order by creation
	 * date.
	 * 
	 * @param User user
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> listByStreamHash(User user, String hash) throws EInvalidData, EAPIError, EAccessDenied {
		return listByStreamHash(user, hash, 1, 100);
	}
	
	/**
	 * Get a page of push subscriptions for the given stream hash, where
	 * each page contains up to 20 items. Results will be returned in
	 * ascending order by creation date.
	 * 
	 * @param User user The user.
	 * @param int  page The page number to fetch.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> listByStreamHash(User user, String hash, int page) throws EInvalidData, EAPIError, EAccessDenied {
		return listByStreamHash(user, hash, page, 20);
	}
	
	/**
	 * Get a page of push subscriptions for the given stream hash, where
	 * each page contains up to per_page items. Results will be returned in
	 * ascending order by creation date.
	 * 
	 * @param User user     The user.
	 * @param int  page     The page number to fetch.
	 * @param int  per_page The number of items per page.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> listByStreamHash(User user, String hash, int page, int per_page) throws EInvalidData, EAPIError, EAccessDenied {
		return listByStreamHash(user, hash, page, per_page, ORDERBY_CREATED_AT, ORDERDIR_ASC, false);
	}
	
	/**
	 * Get a page of push subscriptions for the given stream hash, where
	 * each page contains up to per_page items. Results will be ordered
	 * according to the supplied ordering parameters.
	 * 
	 * @param User user                The user.
	 * @param int  page                The page number to fetch.
	 * @param int  per_page            The number of items per page.
	 * @param String order_by          The field on which to order the results.
	 * @param String order_dir         The direction of the ordering.
	 * @param boolean include_finished True to include subscriptions against
	 *                                 finished historic queries.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> listByStreamHash(User user, String hash, int page, int per_page, String order_by, String order_dir, boolean include_finished) throws EInvalidData, EAPIError, EAccessDenied {
		return list(user, "hash", hash, page, per_page, order_by, order_dir, include_finished);
	}
	
	/**
	 * Get a list of push subscriptions for the given stream hash. Limited
	 * to 100 results.Results will be returned in ascending order by creation
	 * date.
	 * 
	 * @param User user
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> listByPlaybackId(User user, String playback_id) throws EInvalidData, EAPIError, EAccessDenied {
		return listByPlaybackId(user, playback_id, 1, 100);
	}
	
	/**
	 * Get a page of push subscriptions for the given stream hash, where
	 * each page contains up to 20 items. Results will be returned in
	 * ascending order by creation date.
	 * 
	 * @param User user The user.
	 * @param int  page The page number to fetch.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> listByPlaybackId(User user, String playback_id, int page) throws EInvalidData, EAPIError, EAccessDenied {
		return listByPlaybackId(user, playback_id, page, 20);
	}
	
	/**
	 * Get a page of push subscriptions for the given stream hash, where
	 * each page contains up to per_page items. Results will be returned in
	 * ascending order by creation date.
	 * 
	 * @param User user     The user.
	 * @param int  page     The page number to fetch.
	 * @param int  per_page The number of items per page.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> listByPlaybackId(User user, String playback_id, int page, int per_page) throws EInvalidData, EAPIError, EAccessDenied {
		return listByPlaybackId(user, playback_id, page, per_page, ORDERBY_CREATED_AT, ORDERDIR_ASC, false);
	}
	
	/**
	 * Get a page of push subscriptions for the given stream hash, where
	 * each page contains up to per_page items. Results will be ordered
	 * according to the supplied ordering parameters.
	 * 
	 * @param User user                The user.
	 * @param int  page                The page number to fetch.
	 * @param int  per_page            The number of items per page.
	 * @param String order_by          The field on which to order the results.
	 * @param String order_dir         The direction of the ordering.
	 * @param boolean include_finished True to include subscriptions against
	 *                                 finished historic queries.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static public ArrayList<PushSubscription> listByPlaybackId(User user, String playback_id, int page, int per_page, String order_by, String order_dir, boolean include_finished) throws EInvalidData, EAPIError, EAccessDenied {
		return list(user, "playback_id", playback_id, page, per_page, order_by, order_dir, include_finished);
	}
	
	/**
	 * Get a page of push subscriptions in the given user's account, where
	 * each page contains up to per_page items. Results will be ordered
	 * according to the supplied ordering parameters.
	 * 
	 * @param User user                The user.
	 * @param int  page                The page number to fetch.
	 * @param int  per_page            The number of items per page.
	 * @param String order_by          The field on which to order the results.
	 * @param String order_dir         The direction of the ordering.
	 * @param boolean include_finished True to include subscriptions against
	 *                                 finished historic queries.
	 * @return ArrayList<PushSubscription>
	 * @throws EInvalidData
	 * @throws EAPIError
	 * @throws EAccessDenied
	 */
	static protected ArrayList<PushSubscription> list(User user, String hash_type, String hash, int page, int per_page, String order_by, String order_dir, boolean include_finished) throws EInvalidData, EAPIError, EAccessDenied {
		HashMap<String, String> params = new HashMap<String, String>();

		if (hash_type != null && hash_type.length() > 0) {
			if (!hash_type.equals("hash") && !hash_type.equals("playback_id")) {
				throw new EInvalidData("Hash type is invalid");
			}
			params.put(hash_type, hash);
		}
		
		if (page < 1) {
			throw new EInvalidData("The specified page number is invalid");
		}
		
		if (per_page < 1) {
			throw new EInvalidData("The specified per_page value is invalid");
		}
		
		if (order_by != ORDERBY_ID && order_by != ORDERBY_CREATED_AT) {
			throw new EInvalidData("The specified order_by is not supported");
		}

		if (order_dir != ORDERDIR_ASC && order_dir != ORDERDIR_DESC) {
			throw new EInvalidData("The specified order_dir is not supported");
		}
		
		params.put("page", String.valueOf(page));
		params.put("per_page", String.valueOf(per_page));
		params.put("order_by", order_by);
		params.put("order_dir", order_dir);

		JSONObject res = user.callAPI("push/get", params);

		ArrayList<PushSubscription> retval = new ArrayList<PushSubscription>();

		try {
	        JSONArray subscriptions = res.getJSONArray("subscriptions");
	        for (int i = 0; i < subscriptions.length(); i++) {
	            retval.add(new PushSubscription(user, subscriptions.getJSONObject(i)));
	        }
		} catch (JSONException e) {
			throw new EAPIError("Failed to read the subscriptions from the response");
		}
		
		return retval;
	}
	
	/**
     * Get the most recent push subscription log entries.
     * 
     * @param User user The user making the request.
     * @return ArrayList<LogEntry>
	 * @throws EInvalidData 
	 * @throws EAPIError 
	 * @throws EAccessDenied 
     */
	public static Log getLogs(User user) throws EAPIError, EInvalidData, EAccessDenied {
		return getLogs(user, null);
	}
    
	/**
     * Get the most recent push subscription log entries.
     * 
     * @param User   user The user making the request.
     * @param String id   Optional subscription ID, or null.
     * @return ArrayList<LogEntry>
	 * @throws EInvalidData 
	 * @throws EAPIError 
	 * @throws EAccessDenied 
     */
	public static Log getLogs(User user, String id) throws EAPIError, EInvalidData, EAccessDenied {
		return getLogs(user, id, 1, 20);
	}
    
    /**
     * Page through recent push subscription log entries.
     * 
     * @param User user     The user making the request.
     * @param int  page     Which page to fetch.
     * @param int  per_page Based on this page size.
     * @return ArrayList<LogEntry>
     * @throws EInvalidData 
     * @throws EAPIError 
     * @throws EAccessDenied 
     */
    public static Log getLogs(User user, int page, int per_page) throws EAPIError, EInvalidData, EAccessDenied {
		return getLogs(user, null, page, per_page, ORDERBY_REQUEST_TIME, ORDERDIR_DESC);
    }

    /**
     * Page through recent push subscription log entries.
     * 
     * @param User   user   The user making the request.
     * @param String id     Optional subscription ID, or null.
     * @param int  page     Which page to fetch.
     * @param int  per_page Based on this page size.
     * @return ArrayList<LogEntry>
     * @throws EInvalidData 
     * @throws EAPIError 
     * @throws EAccessDenied 
     */
    public static Log getLogs(User user, String id, int page, int per_page) throws EAPIError, EInvalidData, EAccessDenied {
		return getLogs(user, id, page, per_page, ORDERBY_REQUEST_TIME, ORDERDIR_DESC);
    }

    /**
     * Page through recent push subscription log entries, specifying the sort
     * order.
     * 
     * @param User   user      The user making the request.
     * @param int    page      Which page to fetch.
     * @param int    per_page  Based on this page size.
     * @param String order_by  Which field to sort by.
     * @param String order_dir In asc[ending] or desc[ending] order.
     * @return ArrayList<LogEntry>
     * @throws EAPIError 
     * @throws EInvalidData 
     * @throws EAccessDenied 
     */
    public static Log getLogs(User user, int page, int per_page, String order_by, String order_dir) throws EAPIError, EInvalidData, EAccessDenied {
    	return getLogs(user, null, page, per_page, order_by, order_dir);
    }

    /**
     * Page through recent push subscription log entries, specifying the sort
     * order.
     * 
     * @param User   user      The user making the request.
     * @param int    page      Which page to fetch.
     * @param int    per_page  Based on this page size.
     * @param String order_by  Which field to sort by.
     * @param String order_dir In asc[ending] or desc[ending] order.
     * @return ArrayList<LogEntry>
     * @throws EAPIError 
     * @throws EInvalidData 
     * @throws EAccessDenied 
     */
    public static Log getLogs(User user, String id, int page, int per_page, String order_by, String order_dir) throws EAPIError, EInvalidData, EAccessDenied {
		HashMap<String, String> params = new HashMap<String, String>();

		if (page < 1) {
			throw new EInvalidData("The specified page number is invalid");
		}
		
		if (per_page < 1) {
			throw new EInvalidData("The specified per_page value is invalid");
		}
		
		if (order_by != ORDERBY_REQUEST_TIME) {
			throw new EInvalidData("The specified order_by is not supported");
		}

		if (order_dir != ORDERDIR_ASC && order_dir != ORDERDIR_DESC) {
			throw new EInvalidData("The specified order_dir is not supported");
		}
		
		if (id != null && id.length() > 0) {
			params.put("id", id);
		}
		params.put("page", String.valueOf(page));
		params.put("per_page", String.valueOf(per_page));
		params.put("order_by", order_by);
		params.put("order_dir", order_dir);

		JSONObject res = user.callAPI("push/log", params);

		Log retval = null;

		try {
			retval = new Log(res);
		} catch (JSONException e) {
			throw new EAPIError("Failed to read the logs from the response: " + e.getMessage());
		}
		
		return retval;
    }

	protected String _id = "";
	protected Date _created_at = null;
	protected String _status = "";
	protected String _hash = "";
	protected String _hash_type = "";
	protected Date _last_request = null;
	protected Date _last_success = null;
	protected boolean _deleted = false;
	
	public PushSubscription(User user, JSONObject json) throws EInvalidData {
		super(user);
		init(json);
	}
	
	protected void init(JSONObject json) throws EInvalidData {
		try {
			_id = json.getString("id");
		} catch (JSONException e) {
			throw new EInvalidData("No id found");
		}
		
		try {
			_name = json.getString("name");
		} catch (JSONException e) {
			throw new EInvalidData("No name found");
		}
		
		try {
			_created_at = new Date(json.getLong("created_at") * 1000);
		} catch (JSONException e) {
			throw new EInvalidData("No created_at found");
		}
		
		try {
			_status = json.getString("status");
		} catch (JSONException e) {
			throw new EInvalidData("No status found");
		}
		
		try {
			_hash_type = json.getString("hash_type");
		} catch (JSONException e) {
			throw new EInvalidData("No hash_type found");
		}
		
		try {
			_hash = json.getString("hash");
		} catch (JSONException e) {
			throw new EInvalidData("No hash found");
		}
		
		try {
			_last_request = new Date(json.getLong("last_request") * 1000);
		} catch (JSONException e) {
			_last_request = null;
		}
		
		try {
			_last_success = new Date(json.getLong("last_success") * 1000);
		} catch (JSONException e) {
			_last_success = null;
		}
		
		try {
			_output_type = json.getString("output_type");
		} catch (JSONException e) {
			throw new EInvalidData("No output_type found");
		}
		
		try {
			_output_params.clear();
			_output_params.parse(json.getJSONObject("output_params"));
		} catch (JSONException e) {
			throw new EInvalidData("No valid output_params found");
		}
	}
	
	public void reload() throws EInvalidData, EAPIError, EAccessDenied {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", getId());
		init(_user.callAPI("push/get", params));
	}
	
	public String getId() {
		return _id;
	}
	
	public boolean hasId() {
		return getId().length() > 0;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) throws EInvalidData {
		if (isDeleted()) {
			throw new EInvalidData("Cannot modify a deleted subscription");
		}
		super.setName(name);
	}

	public void setOutputParam(String key, String val) throws EInvalidData {
		if (isDeleted()) {
			throw new EInvalidData("Cannot modify a deleted subscription");
		}
		super.setOutputParam(key, val);
	}

	public Date getCreatedAt() {
		return _created_at;
	}

	public String getStatus() {
		return _status;
	}
	
	public boolean isDeleted() {
		return getStatus() == STATUS_DELETED;
	}
	
	public String getHashType() {
		return _hash_type;
	}
	
	public String getHash() {
		return _hash;
	}
	
	public String getOutputType() {
		return _output_type;
	}
	
	public Date getLastRequest() {
		return _last_request;
	}
	
	public Date getLastSuccess() {
		return _last_success;
	}
	
	public void save() throws EInvalidData, EAPIError, EAccessDenied {
		// The output params and name are sent whether creating or updating.
		HashMap<String, String> params = new HashMap<String, String>();
		for (String key : _output_params.keySet()) {
			params.put("output_params." + key, _output_params.get(key));
		}
		params.put("name", getName());

		String endpoint = "push/";
		if (!hasId()) {
			// Never saved, create it
			endpoint += "create";
			
			// Add the hash/playback_id
			if (getHashType().equals(HASH_TYPE_STREAM)) {
				params.put("hash", getHash());
			} else if (getHashType().equals(HASH_TYPE_HISTORIC)) {
				params.put("playback_id", getHash());
			} else {
				throw new EInvalidData("Unknown hash_type: \"" + getHashType() + "\"");
			}
			
			// Output type
			params.put("output_type", getOutputType());
			
			// Add the initial status if it's not empty
			if (getStatus().length() > 0) {
				params.put("initial_status", getStatus());
			}
		} else {
			// Already been saved, do an update
			endpoint += "update";
			
			// ID
			params.put("id", getId());
		}

		// Call the API and pass the returned object into init to update this object
		init(_user.callAPI(endpoint, params));
	}
	
	public void pause() throws EInvalidData, EAPIError, EAccessDenied {
		// Only call the API if we've got an ID (i.e. this subscription has
		// been saved)
		if (hasId()) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", String.valueOf(getId()));
			init(_user.callAPI("push/pause", params));
		} else {
			_status = STATUS_PAUSED;
		}
	}
	
	public void resume() throws EInvalidData, EAPIError, EAccessDenied {
		// Only call the API if we've got an ID (i.e. this subscription has
		// been saved)
		if (hasId()) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", String.valueOf(getId()));
			init(_user.callAPI("push/resume", params));
		} else {
			_status = STATUS_ACTIVE;
		}
	}
	
	public void stop() throws EInvalidData, EAPIError, EAccessDenied {
		// Only call the API if we've got an ID (i.e. this subscription has
		// been saved)
		if (hasId()) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", String.valueOf(getId()));
			init(_user.callAPI("push/stop", params));
		} else {
			_status = STATUS_STOPPED;
		}
	}
	
	public void delete() throws EAPIError, EAccessDenied {
		// Only call the API if we've got an ID (i.e. this subscription has
		// been saved)
		if (hasId()) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", String.valueOf(getId()));
			_user.callAPI("push/delete", params);
		}
		_status = STATUS_DELETED;
	}
	
	public Log getLog() throws EAPIError, EInvalidData, EAccessDenied {
		return getLogs(_user, getId());
	}
	
	public Log getLog(int page, int per_page) throws EAPIError, EInvalidData, EAccessDenied {
		return getLogs(_user, getId(), page, per_page);
	}
	
	public Log getLog(int page, int per_page, String order_by, String order_dir) throws EAPIError, EInvalidData, EAccessDenied {
		return getLogs(_user, getId(), page, per_page, order_by, order_dir);
	}
}
