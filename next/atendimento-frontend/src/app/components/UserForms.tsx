"use client"

import { Button, Field, Input, Stack } from "@chakra-ui/react"
import { useForm } from "react-hook-form"

interface FormValues {
  firstName: string
  lastName: string
}

export function UserForms(props) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>()

  const onSubmit = handleSubmit((data) => console.log(data))
  if (props.action == "Edit")  {
    return (
      <form onSubmit={onSubmit}>
        <Stack gap="4" align="flex-start">
          
          <Field.Root invalid={!!errors.firstName}>
            <Field.Label>First name</Field.Label>
            <Input {...register("firstName")} />
            <Field.ErrorText>{errors.firstName?.message}</Field.ErrorText>
          </Field.Root>

          <Field.Root invalid={!!errors.lastName}>
            <Field.Label>sadgsdggsadfasfe</Field.Label>
            <Input {...register("lastName")} />
            <Field.ErrorText>{errors.lastName?.message}</Field.ErrorText>
          </Field.Root>

          <Field.Root invalid={!!errors.lastName}>
            <Field.Label>asfgasfgafsde</Field.Label>
            <Input {...register("lastName")} />
            <Field.ErrorText>{errors.lastName?.message}</Field.ErrorText>
          </Field.Root>

          <Field.Root invalid={!!errors.lastName}>
            <Field.Label>asfdgafdg</Field.Label>
            <Input {...register("lastName")} />
            <Field.ErrorText>{errors.lastName?.message}</Field.ErrorText>
          </Field.Root>

          <Button type="submit">Submit</Button>
        </Stack>
      </form>
    )
  } else return;
}