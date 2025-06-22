import { Button, CloseButton, Dialog, Portal } from "@chakra-ui/react";

export function LoginDialog(props:any) {
  if (props.action == "View") {
    return (
      <Dialog.Root>
        <Dialog.Trigger asChild>
          <Button variant="outline" size="sm">
            {props.action}
          </Button>
        </Dialog.Trigger>
        <Portal>
          <Dialog.Backdrop />
          <Dialog.Positioner>
            <Dialog.Content>
              <Dialog.Header>
                <Dialog.Title>Case: {props.caseContext.label}</Dialog.Title>
              </Dialog.Header>
              <Dialog.Body>
                <p>Details: {props.caseContext.label}</p>
                <p>Details: {props.caseContext.value}</p>
                <p>Details: {props.caseContext.diff}</p>
              </Dialog.Body>
              <Dialog.Footer>
                <Dialog.ActionTrigger asChild>
                  <Button variant="outline">Cancel</Button>
                </Dialog.ActionTrigger>
                <Button>Save</Button>
              </Dialog.Footer>
              <Dialog.CloseTrigger asChild>
                <CloseButton size="sm" />
              </Dialog.CloseTrigger>
            </Dialog.Content>
          </Dialog.Positioner>
        </Portal>
      </Dialog.Root>
    );
  } else if (props.action == "Edit") {
    return (
      <Dialog.Root>
        <Dialog.Trigger asChild>
          <Button variant="outline" size="sm">
            {props.action}
          </Button>
        </Dialog.Trigger>
        <Portal>
          <Dialog.Backdrop />
          <Dialog.Positioner>
            <Dialog.Content>
              <Dialog.Header>
                <Dialog.Title>
                  {props.action} Case {props.caseContext.label}
                </Dialog.Title>
              </Dialog.Header>
              <Dialog.Body>
                <CaseForms action={props.action}></CaseForms>
              </Dialog.Body>
              <Dialog.Footer>
                <Dialog.ActionTrigger asChild>
                  <Button variant="outline">Cancel</Button>
                </Dialog.ActionTrigger>
                <Button>Save</Button>
              </Dialog.Footer>
              <Dialog.CloseTrigger asChild>
                <CloseButton size="sm" />
              </Dialog.CloseTrigger>
            </Dialog.Content>
          </Dialog.Positioner>
        </Portal>
      </Dialog.Root>
    );
  } else if (props.action == "Delete") {
    return (
      <Dialog.Root role="alertdialog">
        <Dialog.Trigger asChild>
          <Button variant="outline" size="sm">
            {props.action}
          </Button>
        </Dialog.Trigger>
        <Portal>
          <Dialog.Backdrop />
          <Dialog.Positioner>
            <Dialog.Content>
              <Dialog.Header>
                <Dialog.Title>Are you sure?</Dialog.Title>
              </Dialog.Header>
              <Dialog.Body>
                <p>
                  {props.caseContext.value}
                  {props.action} This action cannot be undone. This will
                  permanently delete your account and remove your data from our
                  systems.
                </p>
              </Dialog.Body>
              <Dialog.Footer>
                <Dialog.ActionTrigger asChild>
                  <Button variant="outline">Cancel</Button>
                </Dialog.ActionTrigger>
                <Button colorPalette="red">Delete</Button>
              </Dialog.Footer>
              <Dialog.CloseTrigger asChild>
                <CloseButton size="sm" />
              </Dialog.CloseTrigger>
            </Dialog.Content>
          </Dialog.Positioner>
        </Portal>
      </Dialog.Root>
    );
  }
}
