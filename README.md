# AdvancedRoleBot
## About
`AdvancedRoleBot` is the successor role bot to my project named `SimpleRoleBot` which I just never chose to update to use the latest JDA API. `AdvancedRoleBot` uses improved methods of the Discord API to make managing roles simple and easy for members you don't exactly trust with the `Manage Roles` permission... `Manage Roles` gives way too many permissions. There may be a use case for a server where you only want certain roles to be able to add and remove roles from members. In this case, this is where `AdvancedRoleBot` comes in. Manage what roles a member with a specific role can add and/or remove from other members within your server.
## Requirements
* Java 18+
* Discord Bot with privileged intents
## How to use
Configure the `config.yml` to your liking by following the similar format provided below. Using `*` within an `Add` or `Remove` section for a role specification will allow the member with said role to be able to add/remove any roles that are below the role. For example, if a `Staff` role is above all the roles in my Discord server, all roles under it can be added or removed as long as it is under the `Staff` role. Always use role IDs within the configuration as role names will not work...

Refer to https://docs.badger.store/fivem-general-help/getting-discord-role-id for information on how to get a role ID for Discord
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
      Add:
        - '*' # All roles below the staff role...
      Remove:
        - '*' # All roles below staff role...
    932655785190764573: # Member role
      Add:
        - '1063977112534523936' # new role
      Remove:
        - '1063977112534523936' # new role
```