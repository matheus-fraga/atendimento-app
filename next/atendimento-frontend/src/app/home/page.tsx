"use client"

import { redirect } from "next/navigation";
import { Box, Card  } from "@chakra-ui/react"
import { Grid, GridItem, Button } from "@chakra-ui/react"
import { Cases } from "../../app/components/Cases"
import { Users } from "../../app/components/Users"
import { useState } from "react"

export default function CasesComponent() {
    const [showCases, setShowCases] = useState(false)
    const [showUsers, setShowUsers] = useState(false)
    
    function setShowCasesFunc() {
      resetStateVariables()
      setShowCases(true);
    }
    function setShowUsersFunc() {
      resetStateVariables()
      setShowUsers(true);
    }
    function resetStateVariables(){
      setShowCases(false);
      setShowUsers(false);
    }
    
    return (
    <Box background="tomato" width="100%" padding="1" color="white">
    <Box background="gray" width="100%" padding="1" color="white">
    <Grid
          templateColumns="repeat(10, 1fr)"
          gap={1}
        >
          <GridItem colSpan={1}>
            <Button width="100%" onClick={() => setShowCasesFunc()}>
              Cases
            </Button>
          </GridItem>
          <GridItem colSpan={1}>
             <Button width="100%" onClick={() => setShowUsersFunc()}>
              Users
            </Button>
          </GridItem >
          <GridItem colSpan={1} colStart={10} >
            <Button width="100%">
              <a href="/">Logout</a>
            </Button>
          </GridItem>
        </Grid>
    </Box>
        {showCases && <Cases />}
        {showUsers && <Users />}
    </Box>
  );
}