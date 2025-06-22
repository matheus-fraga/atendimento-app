"use client"

import { Button, Card, Field, Input, Stack, AbsoluteCenter } from "@chakra-ui/react"
import { useForm } from "react-hook-form";
import { useEffect, useState } from "react";
import { log } from "console";
import { LoginDialog } from "./LoginDialog";


interface FormValues {
  username: string
  password: string
}

export function Login () {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [integrationFailed, setIntegrationFailed] = useState(false);
    const [integrationSucess, setIntegrationSucess] = useState(false);
    const [integrationFeedbackMsg, setIntegrationFeedbackMsg] = useState("");
    async function performAuth(props:any) {
      // console.log("SENT");
      // console.log(props);
      // console.log(props.username);
      // console.log(props.password);
      // setLoading(true);
      try {
        const res = await fetch('/api/auth', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username: props.username,
            password: props.password,
          }),
        });
        const data = await res.json();
        console.log(res);
        if (!res.ok) {
          console.log("nok")
           throw new Error(data.error || 'Login failed');
        } 
        setIntegrationFailed(false);
        setIntegrationSucess(true);
        setIntegrationFeedbackMsg("Login success!");
      } catch (err:any) {
        setIntegrationFeedbackMsg(err.toString());
        setIntegrationFailed(true);
        setIntegrationSucess(null);
      } finally {
        setLoading(false);
      }
    }
      
    const {
      register,
      handleSubmit,
      formState: { errors },
    } = useForm<FormValues>()

    const onSubmit = handleSubmit((data) => {console.log(data); performAuth(data);})
    return (
    <AbsoluteCenter axis="both">
      <form onSubmit={onSubmit}>
      <Card.Root maxW="sm">
        <Card.Header>
          <Card.Title>Sign in</Card.Title>
        </Card.Header>
        <Card.Body>
          
            <Stack gap="4" align="flex-start" maxW="sm">
              <Field.Root invalid={!!errors.username}>
                <Field.Label>First name</Field.Label>
                <Input {...register("username")} />
                <Field.ErrorText>{errors.username?.message}</Field.ErrorText>
              </Field.Root>

              <Field.Root invalid={!!errors.password}>
                <Field.Label>Last name</Field.Label>
                <Input {...register("password")} />
                <Field.ErrorText>{errors.password?.message}</Field.ErrorText>
              </Field.Root>
            </Stack>
        </Card.Body>
        <Card.Footer justifyContent="flex-end">
          {loading && <p>loading...</p>}
          { (integrationFailed || integrationSucess) && <p>{integrationFeedbackMsg}</p>}
          <Button type="submit" variant="solid">Sign in</Button>
        </Card.Footer>
      </Card.Root>
      </form>
    </AbsoluteCenter>
  )
}
