import { Button, Card, Field, Input, Stack, AbsoluteCenter } from "@chakra-ui/react"

export const Login = () => (
  <AbsoluteCenter axis="both">
    <Card.Root maxW="sm">
      <Card.Header>
        <Card.Title>Sign in</Card.Title>
      </Card.Header>
      <Card.Body>
        <Stack gap="2" w="full">
          <Field.Root>
            <Field.Label>Username</Field.Label>
            <Input />
          </Field.Root>
          <Field.Root>
            <Field.Label>Password</Field.Label>
            <Input />
          </Field.Root>
        </Stack>
      </Card.Body>
      <Card.Footer justifyContent="flex-end">
        <Button variant="solid"><a href="/home">Sign in</a></Button>
      </Card.Footer>
    </Card.Root>
  </AbsoluteCenter>
)
