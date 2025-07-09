package com.dabi.nba_players_list.data.remote

import com.dabi.nba_players_list.R


/**
 * Represents different API error types with their corresponding HTTP status codes
 * and string resource IDs for localized error messages.
 *
 * @property code The HTTP status code associated with the error.
 */
enum class ApiError(val code: Int) {
    UNAUTHORIZED(401) {
        override fun getStringId(): Int {
            return R.string.error_unauthorized
        }
    },
    BAD_REQUEST(400) {
        override fun getStringId(): Int {
            return R.string.error_bad_request
        }
    },
    NOT_FOUND(404){
        override fun getStringId(): Int {
            return R.string.error_not_found
        }
    },
    NOT_ACCEPTABLE(406){
        override fun getStringId(): Int {
            return R.string.error_not_acceptable
        }
    },
    TOO_MANY_REQUESTS(429){
        override fun getStringId(): Int {
            return R.string.error_too_many_requests
        }
    },
    INTERNAL_SERVER_ERROR(500){
        override fun getStringId(): Int {
            return R.string.error_internal_server_error
        }
    },
    SERVICE_UNAVAILABLE(503){
        override fun getStringId(): Int {
            return R.string.error_server_unavailable
        }
    };

    abstract fun getStringId(): Int
}