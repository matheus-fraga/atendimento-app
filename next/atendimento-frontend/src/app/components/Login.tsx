"use client"

import { Button, Card, Field, Input, Stack, AbsoluteCenter } from "@chakra-ui/react"
import { useForm } from "react-hook-form";

interface FormValues {
  firstName: string
  lastName: string
}

export function Login () {
    const {
      register,
      handleSubmit,
      formState: { errors },
    } = useForm<FormValues>()

    const onSubmit = handleSubmit((data) => console.log(data))
    return (
    <AbsoluteCenter axis="both">
      <Card.Root maxW="sm">
        <Card.Header>
          <Card.Title>Sign in</Card.Title>
        </Card.Header>
        <Card.Body>
          <form onSubmit={onSubmit}>
            <Stack gap="4" align="flex-start" maxW="sm">
              <Field.Root invalid={!!errors.firstName}>
                <Field.Label>First name</Field.Label>
                <Input {...register("firstName")} />
                <Field.ErrorText>{errors.firstName?.message}</Field.ErrorText>
              </Field.Root>

              <Field.Root invalid={!!errors.lastName}>
                <Field.Label>Last name</Field.Label>
                <Input {...register("lastName")} />
                <Field.ErrorText>{errors.lastName?.message}</Field.ErrorText>
              </Field.Root>

              <Button type="submit">Submit</Button>
            </Stack>
          </form>
        </Card.Body>
        <Card.Footer justifyContent="flex-end">
          <Button variant="solid">Sign in</Button>
        </Card.Footer>
      </Card.Root>
    </AbsoluteCenter>
  )
}
