#!/bin/bash

# Get admin token
ADMIN_TOKEN=$(curl -s -X POST "http://localhost:8181/realms/master/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password&client_id=admin-cli&username=admin&password=admin" | jq -r '.access_token')

echo "Admin token obtained"

# Create realm
curl -X POST "http://localhost:8181/admin/realms" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "realm": "fitness-oauth2",
    "enabled": true,
    "displayName": "Fitness OAuth2",
    "loginTheme": "keycloak",
    "registrationAllowed": true,
    "rememberMe": true,
    "registrationEmailAsUsername": false,
    "verifyEmail": false,
    "loginWithEmailAllowed": false,
    "bruteForceProtected": true
  }'

echo "Realm created with username/password registration"

# Wait a moment for realm to be ready
sleep 3

# Create USER role
curl -X POST "http://localhost:8181/admin/realms/fitness-oauth2/roles" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "USER",
    "description": "Regular user role",
    "composite": false,
    "clientRole": false,
    "containerId": "fitness-oauth2"
  }'

echo "USER role created"

# Create client
curl -X POST "http://localhost:8181/admin/realms/fitness-oauth2/clients" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "oauth2-pkce-client",
    "name": "OAuth2 PKCE Client",
    "enabled": true,
    "publicClient": true,
    "standardFlowEnabled": true,
    "redirectUris": ["http://localhost:5173/*"],
    "webOrigins": ["http://localhost:5173"],
    "protocolMappers": [
      {
        "name": "given_name",
        "protocol": "openid-connect",
        "protocolMapper": "oidc-userinfo-attribute-mapper",
        "config": {
          "claim.name": "given_name",
          "jsonType.label": "String",
          "user.attribute": "given_name",
          "id.token.claim": "true",
          "access.token.claim": "true"
        }
      },
      {
        "name": "family_name",
        "protocol": "openid-connect",
        "protocolMapper": "oidc-userinfo-attribute-mapper",
        "config": {
          "claim.name": "family_name",
          "jsonType.label": "String",
          "user.attribute": "family_name",
          "id.token.claim": "true",
          "access.token.claim": "true"
        }
      },
      {
        "name": "preferred_username",
        "protocol": "openid-connect",
        "protocolMapper": "oidc-userinfo-attribute-mapper",
        "config": {
          "claim.name": "preferred_username",
          "jsonType.label": "String",
          "user.attribute": "preferred_username",
          "id.token.claim": "true",
          "access.token.claim": "true"
        }
      }
    ]
  }'

echo "Client created with username/password support"

# Test the realm endpoint
echo "Testing realm endpoint..."
curl -s "http://localhost:8181/realms/fitness-oauth2/.well-known/openid-configuration" | jq -r '.issuer'

echo "Setup complete!"
