# No secrets check

Objetivo: detectar credenciales en archivos versionados.

Comandos sugeridos:

```bash
grep -RInE "password:|api-key:|secret|token|UserName" src knowledge || true
```

Si falla: remover secreto, rotar credenciales si fueron expuestas y documentar.
