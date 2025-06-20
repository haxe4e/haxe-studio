# Coding Guidelines and Best Practices

## Naming Conventions

### Acronyms in Names

1. For **4+ letter acronyms**, use lowercase. E.g. `LdapUtils`, `HttpURL`.

1. For **2 and 3 letter acronyms**, use uppercase. E.g. `IOUtils`, `XMLUtils`.

### Interfaces and Implementing Classes

1. Do **NOT** prefix interfaces with `I`.
   Interface names should represent their role in the system without any syntactic markers.
   E.g. `UserService`, not `IUserService`.

1. Do **NOT** postfix implementations with `Impl`.
   Instead, focus on meaningful and descriptive names.
   E.g. `LdapUserService`, not `UserServiceImpl`.

1. **Descriptive naming for multiple implementations**:
   If there are multiple implementations of an interface, make sure each class name reflects its purpose or underlying mechanism, e.g. `LdapUserService`, `JdbcUserService`.

1. If only **one implementation** exists and the interface is required only for technical purposes (e.g., remoting),
   **prefix the implementation** with `Default`, and use the same name for the interface and the class. E.g. `DefaultUserService`.

### Constants (static final)

1. Use **UPPERCASE** letters and separate words with underscores, e.g. `MAX_LENGTH`, `DEFAULT_TIMEOUT`.


## Source Code Formatting

1. Format your code contributions using the [vegardit.com Eclipse formatter rules](https://github.com/vegardit/vegardit-maven-parent/blob/main/src/etc/eclipse-formatter.xml).

   IntelliJ users can use the [Eclipse Code Formatter](https://plugins.jetbrains.com/plugin/6546-eclipse-code-formatter) plugin to import and use the formatter settings.

   To format the source code on the command line use `mvn process-sources -Dformatter.skip=false`

1. **Consistent line length**: Maintain a maximum line length of **140** characters.

1. **Use spaces, not tabs**: Set the indentation to **3** spaces per level.

1. **Braces placement**: Use the **K&R** style (braces on the same line), e.g.:
   ```java
   if (condition) {
      // action
   } else {
      // action
   }

