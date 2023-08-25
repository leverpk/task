# GitHub Repository List API

This is a simple API that allows you to retrieve a list of GitHub repositories for a given user. The API supports JSON response based on the "Accept" header provided in the request.

## Getting Started

To use this API, make a GET request to the following endpoint:

/api/repositories/{username}

Replace `{username}` with the GitHub username for which you want to retrieve the repositories.

## Request Headers

- `Accept`: Use this header to specify the desired response format. You can set it to either `application/json` for JSON responses.

## Response Formats

The API responds with a list of repositories that meet the specified criteria. Each repository includes the following information:

- Repository Name
- Owner's Login
- For each branch: Name and Last Commit SHA

## Error Handling

If the provided GitHub username does not exist, the API will respond with a 404 status code and an error message.

If the `Accept` header is set to `application/xml` the API will respond with a 406 status code and an error message.