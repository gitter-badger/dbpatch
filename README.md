maven dbpatch plugin
====================

### Manage versioning of your databases with maven
**This plugin allows you to version database changes (sql patches) with maven. It can automaticly apply patches as well as execute rollback scripts.**
_Whenever it is possible patches are executed in transactions, so you can be sure that your patch was applied correctly or none._

#### Plugin goals:
 * dbpatch:help – help screen
 * dbpatch:help-parse – parse sql _(use dbpatch.file system property to indicate file to parse)_
 * dbpatch:list – display list of patches
 * dbpatch:patch – patch database
 * dbpatch:rollback-list – check if there is a rollback file for each patch
 * dbpatch:rollback – rollback patch or multiple patches
 * dbpatch:interactive – interactive mode (GUI) – screen below

#### Bash completions for dbpatch plugin:
Download [bash_completion](./docs/bash_completion) and save it into your maven base directory **~/.m2**.

#### Configuration
For configuration example see [docs/configuration-example](./docs/configuration-example)

#### More info on Wiki
https://github.com/m-szalik/dbpatch-maven-plugin/wiki

#### License
Apache License 2.0