import { Alert, CloseButton } from "@chakra-ui/react"
import { useEffect, useState } from "react";


export function AlertComponent(props) {
  
  const [closeAlert, setCloseAlert] = useState(false)
  function setCloseAlertFunc() {
    setCloseAlert(true);
  }
  if(closeAlert) return null;
  return (
    <Alert.Root status={props.isSuccess ? "success" : "error"}>
      <Alert.Indicator />
      <Alert.Content>
        <Alert.Title>{props.isSuccess ? "success" : "error".toUpperCase()}</Alert.Title>
        <Alert.Description>
          {props.msg}
        </Alert.Description>
      </Alert.Content>
      <CloseButton pos="relative" top="-2" insetEnd="-2" onClick={() => setCloseAlertFunc()}/>
    </Alert.Root>
  )
}