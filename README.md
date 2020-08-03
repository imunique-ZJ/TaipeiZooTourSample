# TaipeiZooTourSample
A sample app for Taipei Zoo tour guide.

# Architecture
- `api` module: Responsible for declaring API inter face and data classes for response data.
- `db` module: Responsible for Room database operations.
- `app` module: Integrate `api` and `db` modules. Follow MVVM and add a `repository` layer
to get data from database and API.

# Things to do...
- Add Hilt to help dependency injection.
- Retrieve Animal Data from TaipeiCity OpenData.
- Enable WGS84 coordinates. (WKT format)
...