# AdvancedRoleBot
## About
`AdvancedRoleBot` is the successor role bot to my project named `SimpleRoleBot` which I just never chose to update to use the latest JDA API. `AdvancedRoleBot` uses improved methods of the Discord API to make managing roles simple and easy for members you don't exactly trust with the `Manage Roles` permission... `Manage Roles` gives way too many permissions. There may be a use case for a server where you only want certain roles to be able to add and remove roles from members. In this case, this is where `AdvancedRoleBot` comes in. Manage what roles a member with a specific role can add and/or remove from other members within your server.
## How to use

## Configuration
```yaml
Config:
  Token: ''
  Commands:
    Role:
      Command: 'arole'
      Sub-Commands:
        Add:
          Command: 'add'
          Embed:
            Title: 'Add a role'
            Thumbnail: ''
            Description: '*Add a role to {MEMBER}*'
            Color: '#ffffff'
        Remove:
          Command: 'remove'
          Embed:
            Title: 'Remove a role'
            Thumbnail: ''
            Description: '*Remove a role from {MEMBER}*'
            Color: '#ffffff'
  Roles:
    932655785299824670: # Staff role in Badger Dev Community
      932655785190764573: # Member role in Badger Dev Community
        Permissions:
          - 'ADD'
          - 'REMOVE'
    932655785190764573: # Member role
      1063977112534523936: # new role
        Permissions:
          - 'ADD'
          - 'REMOVE'
```

## Credits